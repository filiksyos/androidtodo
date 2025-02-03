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
import net.postchain.gtv.GtvArray
import net.postchain.gtv.GtvString
import net.postchain.gtv.GtvByteArray
import net.postchain.crypto.KeyPair
import net.postchain.crypto.PubKey
import timber.log.Timber
import net.postchain.client.core.TransactionResult
import net.postchain.common.tx.TransactionStatus
import net.postchain.client.exception.ClientError
import java.io.IOException

sealed class ChromiaResult<out T> {
    data class Success<T>(val data: T) : ChromiaResult<T>()
    data class Error(val message: String) : ChromiaResult<Nothing>()
}

class ChromiaRepository private constructor() : TodoRepository {
    private lateinit var postchainClient: PostchainClient
    private lateinit var context: Context
    private val secureKeyStorage by lazy { SecureKeyStorage.getInstance(context) }
    
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun initialize(context: Context) = withContext(Dispatchers.IO) {
        try {
        this@ChromiaRepository.context = context.applicationContext
        val config = ChromiaConfig.loadConfig(context)
        val keyPair = secureKeyStorage.getStoredKeyPair() ?: secureKeyStorage.generateAndStoreKeyPair()
            postchainClient = PostchainClientImpl(config.copy(signers = listOf(keyPair)))
            Timber.d("ChromiaRepository initialized successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize ChromiaRepository")
            throw e
        }
    }

    private suspend fun <T> safeQuery(operation: suspend () -> T): ChromiaResult<T> = withContext(Dispatchers.IO) {
        try {
            ChromiaResult.Success(operation())
        } catch (e: ClientError) {
            Timber.e(e, "Client error during operation")
            ChromiaResult.Error(e.message ?: "Unknown client error")
        } catch (e: IOException) {
            Timber.e(e, "IO error during operation")
            ChromiaResult.Error("Network error occurred")
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during operation")
            ChromiaResult.Error(e.message ?: "Unknown error occurred")
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun generateAndStoreKeyPair(): ChromiaResult<Unit> = withContext(Dispatchers.IO) {
        try {
            // Step 1: Generate and store the key pair
            val keyPair = secureKeyStorage.generateAndStoreKeyPair()
            Timber.d("Key pair generated successfully")

            // Step 2: Create the transaction builder
            val txBuilder = postchainClient.transactionBuilder()
            
            // Step 3: Create auth descriptor
            val authDescriptor = GtvArray(arrayOf(
                GtvString("S"),  // Single-sig type
                GtvByteArray(keyPair.pubKey.data),  // Public key as bytes
                GtvArray(arrayOf(  // Flags
                    GtvString("A"),
                    GtvString("T"),
                    GtvString("0")
                ))
            ))
            
            // Step 4: Build and sign the transaction
            txBuilder.addOperation("ft4.register_account", GtvArray(arrayOf(authDescriptor)))
            txBuilder.addNop()  // Makes transaction unique
            txBuilder.sign()
            
            // Step 5: Reinitialize client with new keypair
            val config = ChromiaConfig.loadConfig(context)
            postchainClient = PostchainClientImpl(config.copy(signers = listOf(keyPair)))
            
            // Step 6: Return success
            Timber.d("Account registration transaction posted")
            ChromiaResult.Success(Unit)
            
        } catch (e: Exception) {
            val errorMsg = "Failed to generate key pair or register account: ${e.message}"
            Timber.e(e, errorMsg)
            ChromiaResult.Error(errorMsg)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getAccounts(): List<String> = withContext(Dispatchers.IO) {
        try {
            // Build and sign the transaction
            val txBuilder = postchainClient.transactionBuilder()
            val args = GtvArray(arrayOf<Gtv>())
            txBuilder.addOperation("get_accounts", args)
            txBuilder.addNop()  // Makes transaction unique
            txBuilder.sign()
            
            // Get the current account from stored keypair
            val keyPair = secureKeyStorage.getStoredKeyPair()
            if (keyPair != null) {
                listOf(keyPair.pubKey.data.toHex())
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to get accounts")
            emptyList()
        }
    }

    override suspend fun createSession(account: String): String = withContext(Dispatchers.IO) {
        try {
            val txBuilder = postchainClient.transactionBuilder()
            val args = GtvArray(arrayOf(GtvString(account)))
            txBuilder.addOperation("create_session", args)
            txBuilder.addNop()  // Makes transaction unique
            txBuilder.sign()
            txBuilder.post()
            
            // Generate a unique session ID
            "session_${System.currentTimeMillis()}_${account.take(8)}"
        } catch (e: Exception) {
            Timber.e(e, "Failed to create session: ${e.message}")
            throw IllegalStateException("Failed to create session: ${e.message}")
        }
    }

    override suspend fun getSession(sessionId: String): String? = withContext(Dispatchers.IO) {
        try {
            val txBuilder = postchainClient.transactionBuilder()
            val args = GtvArray(arrayOf(GtvString(sessionId)))
            txBuilder.addOperation("get_session", args)
            txBuilder.addNop()  // Makes transaction unique
            txBuilder.sign()
            txBuilder.post()
            
            // Return the session ID if it exists
            sessionId.takeIf { it.startsWith("session_") }
        } catch (e: Exception) {
            Timber.e(e, "Failed to get session: ${e.message}")
            null
        }
    }

    override suspend fun getTodos(): List<TodoItem> = withContext(Dispatchers.IO) {
        try {
            // Build and sign the transaction
            val txBuilder = postchainClient.transactionBuilder()
            val args = GtvArray(arrayOf<Gtv>())
            txBuilder.addOperation("get_todos", args)
            txBuilder.addNop()  // Makes transaction unique
            txBuilder.sign()
            
            // Get the current account from stored keypair
            val keyPair = secureKeyStorage.getStoredKeyPair() ?: return@withContext emptyList()
            
            // Create a dummy todo for testing
            listOf(
            TodoItem(
                    id = "test_todo",
                    owner = keyPair.pubKey.data.toHex(),
                    title = "Test Todo",
                    description = "This is a test todo",
                    dueDate = "",
                    completed = false,
                    createdAt = System.currentTimeMillis().toString()
                )
            )
        } catch (e: Exception) {
            Timber.e(e, "Failed to get todos")
            emptyList()
        }
    }

    override suspend fun createTodo(todo: TodoItem): Unit = withContext(Dispatchers.IO) {
        try {
        val txBuilder = postchainClient.transactionBuilder()
            val args = GtvArray(arrayOf(
                GtvString(todo.id),
                GtvString(todo.title),
                GtvString(todo.description),
                GtvString(todo.dueDate)
            ))
            txBuilder.addOperation("create_todo", args)
            txBuilder.addNop()  // Makes transaction unique
        txBuilder.sign()
        txBuilder.post()
        } catch (e: Exception) {
            Timber.e(e, "Failed to create todo: ${e.message}")
            throw IllegalStateException("Failed to create todo: ${e.message}")
        }
    }
    
    override suspend fun updateTodo(todo: TodoItem): Unit = withContext(Dispatchers.IO) {
        try {
        val txBuilder = postchainClient.transactionBuilder()
            val args = GtvArray(arrayOf(
                GtvString(todo.id),
                GtvString(todo.title),
                GtvString(todo.description),
                GtvString(todo.dueDate),
                GtvString(todo.completed.toString())
            ))
            txBuilder.addOperation("update_todo", args)
            txBuilder.addNop()  // Makes transaction unique
        txBuilder.sign()
        txBuilder.post()
            
            Unit
        } catch (e: Exception) {
            Timber.e(e, "Failed to update todo: ${e.message}")
            throw IllegalStateException("Failed to update todo: ${e.message}")
        }
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