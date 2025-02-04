package com.example.data.security

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.example.data.util.IOWrapper
import net.postchain.crypto.KeyPair
import net.postchain.crypto.Secp256K1CryptoSystem
import timber.log.Timber

class SecureKeyStorage private constructor(private val context: Context) {
    private val cryptoSystem = Secp256K1CryptoSystem()
    private val PREFS_NAME = "ChromiaPrefs"
    private val KEY_KEYPAIR = "keypair"
    
    companion object {
        private var instance: SecureKeyStorage? = null
        
        fun getInstance(context: Context): SecureKeyStorage {
            return instance ?: SecureKeyStorage(context).also { instance = it }
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun generateAndStoreKeyPair(): KeyPair {
        Timber.d("Generating new KeyPair")
        val keyPair = cryptoSystem.generateKeyPair()
        storeKeyPair(keyPair)
        return keyPair
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun getStoredKeyPair(): KeyPair? {
        Timber.d("Attempting to retrieve stored KeyPair")
        return try {
            val base64KeyPair = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_KEYPAIR, null)
            
            if (base64KeyPair != null) {
                val keyPairBytes = IOWrapper.fromBase64(base64KeyPair)
                Timber.d("Successfully retrieved KeyPair bytes: ${keyPairBytes.size} bytes")
                // TODO: Implement actual KeyPair reconstruction
                cryptoSystem.generateKeyPair() // Placeholder
            } else {
                Timber.d("No stored KeyPair found")
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to retrieve KeyPair")
            null
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun storeKeyPair(keyPair: KeyPair) {
        try {
            // TODO: Implement actual KeyPair serialization
            val keyPairBytes = ByteArray(32) // Placeholder
            val base64KeyPair = IOWrapper.toBase64(keyPairBytes)
            
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_KEYPAIR, base64KeyPair)
                .apply()
            
            Timber.d("Successfully stored KeyPair")
        } catch (e: Exception) {
            Timber.e(e, "Failed to store KeyPair")
            throw e
        }
    }
} 