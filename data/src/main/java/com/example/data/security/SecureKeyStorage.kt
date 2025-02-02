package com.example.data.security

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import net.postchain.crypto.KeyPair
import net.postchain.crypto.Secp256K1CryptoSystem
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64
import androidx.annotation.RequiresApi

class SecureKeyStorage private constructor(private val context: Context) {
    private val cryptoSystem = Secp256K1CryptoSystem()
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val keyAlias = "ChromiaKeyPair"
    
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
        val encryptedData = context.getSharedPreferences("ChromiaPrefs", Context.MODE_PRIVATE)
            .getString("encrypted_keypair", null) ?: return null
            
        return try {
            val decryptedData = decrypt(Base64.decode(encryptedData, Base64.DEFAULT))
            // Parse decryptedData back to KeyPair
            // Implementation depends on KeyPair serialization format
            null // TODO: Implement actual KeyPair deserialization
        } catch (e: Exception) {
            null
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun storeKeyPair(keyPair: KeyPair) {
        // TODO: Implement KeyPair serialization
        val serializedData = ByteArray(0) // Placeholder
        
        val encryptedData = encrypt(serializedData)
        context.getSharedPreferences("ChromiaPrefs", Context.MODE_PRIVATE)
            .edit()
            .putString("encrypted_keypair", Base64.encodeToString(encryptedData, Base64.DEFAULT))
            .apply()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getOrCreateSecretKey(): SecretKey {
        return if (keyStore.containsAlias(keyAlias)) {
            (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
        } else {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        
        val encryptedData = cipher.doFinal(data)
        val iv = cipher.iv
        
        return iv + encryptedData
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun decrypt(encryptedData: ByteArray): ByteArray {
        val iv = encryptedData.slice(0..11).toByteArray()
        val encrypted = encryptedData.slice(12..encryptedData.lastIndex).toByteArray()
        
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), GCMParameterSpec(128, iv))
        
        return cipher.doFinal(encrypted)
    }
} 