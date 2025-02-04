package com.example.data.client

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.util.readAllBytes
import net.postchain.client.core.PostchainClient
import net.postchain.client.core.TransactionResult
import net.postchain.client.impl.PostchainClientImpl
import net.postchain.client.transaction.TransactionBuilder
import net.postchain.crypto.KeyPair
import net.postchain.crypto.PubKey
import net.postchain.gtv.Gtv
import net.postchain.gtx.Gtx
import timber.log.Timber
import java.time.Duration
import org.apache.commons.io.input.BoundedInputStream
import java.lang.reflect.Method

class SafePostchainClient(private val delegate: PostchainClient) : PostchainClient by delegate {
    
    private var queryMethod: Method? = null
    private var readAllBytesMethod: Method? = null
    
    init {
        Timber.d("SafePostchainClient - Initializing with delegate: ${delegate.javaClass.name}")
        Timber.d("SafePostchainClient - Delegate type: ${delegate.javaClass.name}")
        Timber.d("SafePostchainClient - Delegate hash: ${System.identityHashCode(delegate)}")
        
        try {
            // Get the actual query implementation through reflection
            queryMethod = delegate.javaClass.getDeclaredMethod("query", String::class.java, Gtv::class.java)
            queryMethod?.isAccessible = true
            
            // Try to get the readAllBytes method if it exists
            readAllBytesMethod = BoundedInputStream::class.java.getDeclaredMethod("readAllBytes")
            readAllBytesMethod?.isAccessible = true
        } catch (e: Exception) {
            Timber.d("SafePostchainClient: Could not get methods through reflection: ${e.message}")
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun query(operation: String, args: Gtv): Gtv {
        Timber.d("SafePostchainClient.query - ENTRY POINT")
        Timber.d("SafePostchainClient.query - Operation: $operation")
        Timber.d("SafePostchainClient.query - Args: $args")
        
        var delegateException: Exception? = null
        var wrapperException: Exception? = null
        
        try {
            Timber.d("SafePostchainClient.query - Attempting delegate.query")
            return delegate.query(operation, args).also {
                Timber.d("SafePostchainClient.query - Query successful")
            }
        } catch (e: Exception) {
            delegateException = e
            Timber.e("SafePostchainClient.query - Delegate query failed")
            Timber.e("SafePostchainClient.query - Error class: ${e.javaClass.name}")
            Timber.e("SafePostchainClient.query - Error message: ${e.message}")
        }
        
        // Only try wrapper if delegate failed
        if (delegateException != null) {
            try {
                Timber.d("SafePostchainClient.query - Attempting wrapper approach")
                
                // Use reflection to get the stream
                val responseStreamMethod = delegate.javaClass.getDeclaredMethod("responseStream")
                responseStreamMethod.isAccessible = true
                
                val boundedInputStream = responseStreamMethod.invoke(delegate) as? BoundedInputStream
                if (boundedInputStream == null) {
                    throw IllegalStateException("Failed to get BoundedInputStream")
                }
                
                Timber.d("SafePostchainClient.query - Got BoundedInputStream")
                boundedInputStream.use { stream ->
                    val bytes = stream.readAllBytes()
                    Timber.d("SafePostchainClient.query - Read ${bytes.size} bytes")
                    return processQueryResponse(bytes, operation)
                }
            } catch (e: Exception) {
                wrapperException = e
                Timber.e("SafePostchainClient.query - Wrapper approach failed")
                Timber.e("SafePostchainClient.query - Error class: ${e.javaClass.name}")
                Timber.e("SafePostchainClient.query - Error message: ${e.message}")
            }
        }
        
        // If we get here, both approaches failed
        throw RuntimeException("Query failed. Delegate error: $delegateException, Wrapper error: $wrapperException")
    }
    
    private fun processQueryResponse(bytes: ByteArray, operation: String): Gtv {
        Timber.d("SafePostchainClient.processQueryResponse - Processing ${bytes.size} bytes for operation: $operation")
        // TODO: Implement proper response processing
        throw NotImplementedError("Response processing not yet implemented")
    }
    
    companion object {
        fun wrap(client: PostchainClient): SafePostchainClient {
            Timber.d("SafePostchainClient.wrap - Creating wrapper for client: ${client.javaClass.name}")
            return SafePostchainClient(client)
        }
    }
} 