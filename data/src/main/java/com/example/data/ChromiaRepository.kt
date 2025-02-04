package com.example.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.config.ChromiaConfig
import com.example.data.security.SecureKeyStorage
import net.postchain.client.core.PostchainClient
import net.postchain.client.impl.PostchainClientImpl
import net.postchain.gtv.Gtv
import net.postchain.gtv.GtvFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds

class ChromiaRepository private constructor() : TodoRepository {
    private lateinit var postchainClient: PostchainClient
    private lateinit var context: Context
    private val secureKeyStorage by lazy { SecureKeyStorage.getInstance(context) }
    private val transactionCache = ConcurrentHashMap<String, TransactionStatus>()
    
    companion object {
        private var instance: ChromiaRepository? = null
        private const val TRANSACTION_TIMEOUT = 30L // seconds
        private const val PREFS_NAME = "ChromiaRepository"
        private const val KEY_CURRENT_ACCOUNT = "current_account"
        private const val KEY_CURRENT_SESSION = "current_session"
        
        fun getInstance(): ChromiaRepository {
            return instance ?: ChromiaRepository().also {
                instance = it
            }
        }
    }
    
    data class TransactionStatus(
        val txId: String,
        var confirmed: Boolean = false,
        var error: String? = null
    )
    
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun initialize(context: Context) = withContext(Dispatchers.IO) {
        try {
        this@ChromiaRepository.context = context.applicationContext
        val config = ChromiaConfig.loadConfig(context)
        val keyPair = secureKeyStorage.getStoredKeyPair() ?: secureKeyStorage.generateAndStoreKeyPair()
        
        postchainClient = PostchainClientImpl(config.copy(signers = listOf(keyPair)))
            
            // Generate a simple account identifier
            val accountId = generateAccountId()
            storeCurrentAccount(accountId)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to initialize Chromia", e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun generateAndStoreKeyPair() = withContext(Dispatchers.IO) {
        val keyPair = secureKeyStorage.generateAndStoreKeyPair()
        // Reinitialize PostchainClient with the new keypair
        val config = ChromiaConfig.loadConfig(context)
        postchainClient = PostchainClientImpl(config.copy(signers = listOf(keyPair)))
        
        // Generate a new account identifier
        val accountId = generateAccountId()
        storeCurrentAccount(accountId)
    }
    
    override suspend fun getAccounts(): List<String> = withContext(Dispatchers.IO) {
        val currentAccount = getCurrentAccount()
        return@withContext if (currentAccount != null) listOf(currentAccount) else emptyList()
    }
    
    override suspend fun createSession(account: String): String = withContext(Dispatchers.IO) {
        val currentAccount = getCurrentAccount()
        if (currentAccount == account) {
            val sessionId = generateSessionId()
            storeCurrentSession(sessionId)
            return@withContext sessionId
        }
        throw IllegalStateException("Account not found")
    }
    
    override suspend fun getSession(sessionId: String): String? = withContext(Dispatchers.IO) {
        val currentSession = getCurrentSession()
        return@withContext if (sessionId == currentSession) currentSession else null
    }

    private fun generateAccountId(): String {
        return "account_${System.currentTimeMillis()}"
    }

    private fun generateSessionId(): String {
        return "session_${System.currentTimeMillis()}"
    }

    private fun storeCurrentAccount(accountId: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CURRENT_ACCOUNT, accountId)
            .apply()
    }

    private fun getCurrentAccount(): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CURRENT_ACCOUNT, null)
    }

    private fun storeCurrentSession(sessionId: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CURRENT_SESSION, sessionId)
            .apply()
    }

    private fun getCurrentSession(): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CURRENT_SESSION, null)
    }
    
    override suspend fun getTodos(): List<TodoItem> = withContext(Dispatchers.IO) {
        val args = GtvFactory.gtv(emptyList<Gtv>())
        val result = postchainClient.query("get_todos", args)
        return@withContext result.asArray().map { todo ->
            val dict = todo.asDict()
            TodoItem(
                id = dict["id"]!!.asString(),
                owner = dict["owner"]!!.asString(),
                title = dict["title"]!!.asString(),
                description = dict["description"]!!.asString(),
                dueDate = dict["due_date"]!!.asString(),
                completed = dict["completed"]!!.asBoolean(),
                createdAt = dict["created_at"]!!.asString()
            )
        }
    }
    
    override suspend fun createTodo(todo: TodoItem): Unit = withContext(Dispatchers.IO) {
        val txBuilder = postchainClient.transactionBuilder()
        val args = listOf(
            GtvFactory.gtv(todo.id),
            GtvFactory.gtv(todo.title),
            GtvFactory.gtv(todo.description),
            GtvFactory.gtv(todo.dueDate)
        )
        txBuilder.addOperation("create_todo", GtvFactory.gtv(args))
        txBuilder.addNop() // Makes transaction unique
        txBuilder.sign()
        txBuilder.post()
    }
    
    override suspend fun updateTodo(todo: TodoItem): Unit = withContext(Dispatchers.IO) {
        val txBuilder = postchainClient.transactionBuilder()
        val args = listOf(
            GtvFactory.gtv(todo.id),
            GtvFactory.gtv(todo.title),
            GtvFactory.gtv(todo.description),
            GtvFactory.gtv(todo.dueDate),
            GtvFactory.gtv(todo.completed)
        )
        txBuilder.addOperation("update_todo", GtvFactory.gtv(args))
        txBuilder.sign()
        txBuilder.post()
    }

    private suspend fun executeTransaction(
        operation: String,
        args: List<Gtv>
    ): Unit = withContext(Dispatchers.IO) {
        var lastError: Exception? = null
        repeat(3) { attempt ->
            try {
                val txBuilder = postchainClient.transactionBuilder()
                txBuilder.addOperation(operation, GtvFactory.gtv(args))
                if (attempt > 0) txBuilder.addNop() // Makes retried transaction unique
                txBuilder.sign()
                val txId = txBuilder.post()
                
                transactionCache[txId.toString()] = TransactionStatus(txId.toString())
                
                // Wait for confirmation
                withTimeout(TRANSACTION_TIMEOUT.seconds) {
                    while (!isTransactionConfirmed(txId.toString())) {
                        kotlinx.coroutines.delay(1000)
                    }
                }
                return@withContext
            } catch (e: Exception) {
                lastError = e
                if (attempt < 2) { // 2 is max retry attempts (3-1)
                    kotlinx.coroutines.delay(1000L * (attempt + 1))
                }
            }
        }
        throw lastError ?: IllegalStateException("Transaction failed after 3 attempts")
    }
    
    private suspend fun isTransactionConfirmed(txId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            // Since getTransactionStatus is not available, we'll use a query
            val args = GtvFactory.gtv(listOf(GtvFactory.gtv(txId)))
            val result = postchainClient.query("get_transaction_status", args)
            val confirmed = result.asBoolean()
            transactionCache[txId]?.confirmed = confirmed
            return@withContext confirmed
        } catch (e: Exception) {
            transactionCache[txId]?.error = e.message
            throw e
        }
    }
    
    override suspend fun deleteTodo(id: String): Unit = withContext(Dispatchers.IO) {
        val args = listOf(GtvFactory.gtv(id))
        executeTransaction("delete_todo", args)
    }
    
    override suspend fun toggleTodo(id: String): Unit = withContext(Dispatchers.IO) {
        val args = listOf(GtvFactory.gtv(id))
        executeTransaction("toggle_todo", args)
    }
    
    fun getTransactionStatus(txId: String): TransactionStatus? {
        return transactionCache[txId]
    }
} 