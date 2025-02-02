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

class ChromiaRepository private constructor() : TodoRepository {
    private lateinit var postchainClient: PostchainClient
    private lateinit var context: Context
    private val secureKeyStorage by lazy { SecureKeyStorage.getInstance(context) }
    
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun initialize(context: Context) = withContext(Dispatchers.IO) {
        this@ChromiaRepository.context = context.applicationContext
        val config = ChromiaConfig.loadConfig(context)
        val keyPair = secureKeyStorage.getStoredKeyPair() ?: secureKeyStorage.generateAndStoreKeyPair()
        
        postchainClient = PostchainClientImpl(config.copy(signers = listOf(keyPair)))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun generateAndStoreKeyPair() = withContext(Dispatchers.IO) {
        val keyPair = secureKeyStorage.generateAndStoreKeyPair()
        // Reinitialize PostchainClient with the new keypair
        val config = ChromiaConfig.loadConfig(context)
        postchainClient = PostchainClientImpl(config.copy(signers = listOf(keyPair)))
    }
    
    override suspend fun getAccounts(): List<String> = withContext(Dispatchers.IO) {
        val args = GtvFactory.gtv(emptyList<Gtv>())
        val result = postchainClient.query("get_accounts", args)
        return@withContext result.asArray().map { it.asString() }
    }
    
    override suspend fun createSession(account: String): String = withContext(Dispatchers.IO) {
        val args = GtvFactory.gtv(listOf(GtvFactory.gtv(account)))
        val result = postchainClient.query("create_session", args)
        return@withContext result.asString()
    }
    
    override suspend fun getSession(sessionId: String): String? = withContext(Dispatchers.IO) {
        val args = GtvFactory.gtv(listOf(GtvFactory.gtv(sessionId)))
        val result = postchainClient.query("get_session", args)
        return@withContext result?.asString()
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

    override suspend fun deleteTodo(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun toggleTodo(id: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private var instance: ChromiaRepository? = null
        
        fun getInstance(): ChromiaRepository {
            return instance ?: ChromiaRepository().also {
                instance = it
            }
        }
    }
} 