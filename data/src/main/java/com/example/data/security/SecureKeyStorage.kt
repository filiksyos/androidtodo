package com.example.data.security

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import net.postchain.crypto.KeyPair
import net.postchain.crypto.Secp256K1CryptoSystem

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
        val keyPair = cryptoSystem.generateKeyPair()
        storeKeyPair(keyPair)
        return keyPair
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun getStoredKeyPair(): KeyPair? {
        val base64KeyPair = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_KEYPAIR, null) ?: return null
            
        return try {
            // Simple storage - just store the keypair as a base64 string
            // This is a simplified version - in production you'd need proper serialization
            val keyPairBytes = Base64.decode(base64KeyPair, Base64.DEFAULT)
            // Assume the keypair can be reconstructed from bytes
            // This is a simplified version - in production you'd need proper serialization
            cryptoSystem.generateKeyPair() // Placeholder - replace with actual keypair reconstruction
        } catch (e: Exception) {
            null
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun storeKeyPair(keyPair: KeyPair) {
        // Simple storage - just store the keypair as a base64 string
        // This is a simplified version - in production you'd need proper serialization
        val keyPairBytes = ByteArray(32) // Placeholder - replace with actual keypair serialization
        val base64KeyPair = Base64.encodeToString(keyPairBytes, Base64.DEFAULT)
        
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_KEYPAIR, base64KeyPair)
            .apply()
    }
} 