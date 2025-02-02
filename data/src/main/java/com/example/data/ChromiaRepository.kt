package com.example.data

import net.postchain.client.core.PostchainClient
import net.postchain.client.core.TransactionResult
import net.postchain.client.config.PostchainClientConfig
import net.postchain.client.impl.PostchainClientImpl
import net.postchain.client.request.EndpointPool
import net.postchain.common.BlockchainRid
import net.postchain.crypto.Secp256K1CryptoSystem
import net.postchain.crypto.KeyPair
import net.postchain.gtv.Gtv
import net.postchain.gtv.GtvFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChromiaRepository {
    private lateinit var postchainClient: PostchainClient
    private val cryptoSystem = Secp256K1CryptoSystem()
    private lateinit var keyPair: KeyPair
    
    suspend fun initialize(nodeUrl: String, brid: String, keyPair: KeyPair) = withContext(Dispatchers.IO) {
        this@ChromiaRepository.keyPair = keyPair
        val blockchainRid = BlockchainRid.buildFromHex(brid)
        val endpoints = EndpointPool.default(listOf(nodeUrl))
        val config = PostchainClientConfig(
            blockchainRid = blockchainRid,
            endpointPool = endpoints,
            signers = listOf(keyPair)
        )
        postchainClient = PostchainClientImpl(config)
    }
    
    suspend fun getAccounts(): List<String> = withContext(Dispatchers.IO) {
        // Using query since direct accounts.list() is not available in the interface
        val args = GtvFactory.gtv(emptyList<Gtv>())
        val result = postchainClient.query("get_accounts", args)
        return@withContext result.asArray().map { it.asString() }
    }
    
    suspend fun createSession(account: String): String = withContext(Dispatchers.IO) {
        // Using query since direct sessions.create() is not available in the interface
        val args = GtvFactory.gtv(listOf(GtvFactory.gtv(account)))
        val result = postchainClient.query("create_session", args)
        return@withContext result.asString()
    }
    
    suspend fun getSession(sessionId: String): String? = withContext(Dispatchers.IO) {
        // Using query since direct sessions.get() is not available in the interface
        val args = GtvFactory.gtv(listOf(GtvFactory.gtv(sessionId)))
        val result = postchainClient.query("get_session", args)
        return@withContext result?.asString()
    }
    
    suspend fun getTodos(): List<TodoItem> = withContext(Dispatchers.IO) {
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
    
    suspend fun createTodo(todo: TodoItem): TransactionResult = withContext(Dispatchers.IO) {
        val txBuilder = postchainClient.transactionBuilder()
        val args = listOf(
            GtvFactory.gtv(todo.id),
            GtvFactory.gtv(todo.title),
            GtvFactory.gtv(todo.description),
            GtvFactory.gtv(todo.dueDate)
        )
        txBuilder.addOperation("create_todo", GtvFactory.gtv(args))
        txBuilder.addNop() // Makes transaction unique
        txBuilder.sign(cryptoSystem.buildSigMaker(keyPair))
        txBuilder.post()
    }
    
    suspend fun updateTodo(todo: TodoItem): TransactionResult = withContext(Dispatchers.IO) {
        val txBuilder = postchainClient.transactionBuilder()
        val args = listOf(
            GtvFactory.gtv(todo.id),
            GtvFactory.gtv(todo.title),
            GtvFactory.gtv(todo.description),
            GtvFactory.gtv(todo.dueDate),
            GtvFactory.gtv(todo.completed)
        )
        txBuilder.addOperation("update_todo", GtvFactory.gtv(args))
        txBuilder.sign(cryptoSystem.buildSigMaker(keyPair))
        txBuilder.post()
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