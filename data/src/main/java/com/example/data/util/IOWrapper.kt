package com.example.data.util

import android.util.Base64
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset

object IOWrapper {
    fun readAllBytes(input: InputStream): ByteArray {
        Timber.d("IOWrapper.readAllBytes - ENTRY POINT")
        Timber.d("IOWrapper.readAllBytes - Input stream type: ${input.javaClass.name}")
        Timber.d("IOWrapper.readAllBytes - Input stream hash: ${System.identityHashCode(input)}")
        
        return try {
            Timber.d("IOWrapper.readAllBytes - Attempting to read bytes using native method")
            val result = input.readBytes()
            Timber.d("IOWrapper.readAllBytes - Successfully read ${result.size} bytes")
            result
        } catch (e: Exception) {
            Timber.e(e, "IOWrapper.readAllBytes - FAILED to read bytes")
            Timber.e("IOWrapper.readAllBytes - Error type: ${e.javaClass.name}")
            Timber.e("IOWrapper.readAllBytes - Error message: ${e.message}")
            Timber.e("IOWrapper.readAllBytes - Stack trace: ${e.stackTrace.joinToString("\n")}")
            throw e
        } finally {
            Timber.d("IOWrapper.readAllBytes - EXIT POINT")
        }
    }

    fun writeBytes(output: OutputStream, bytes: ByteArray) {
        Timber.d("IOWrapper.writeBytes - Starting with ${bytes.size} bytes")
        try {
            output.write(bytes)
            output.flush()
            Timber.d("IOWrapper.writeBytes - Successfully wrote bytes")
        } catch (e: Exception) {
            Timber.e(e, "IOWrapper.writeBytes - Failed to write bytes")
            throw e
        }
    }

    fun toString(input: InputStream, charset: Charset = Charsets.UTF_8): String {
        Timber.d("IOWrapper.toString - Starting conversion with charset ${charset.name()}")
        return try {
            val result = input.reader(charset).use { it.readText() }
            Timber.d("IOWrapper.toString - Successfully converted to string of length ${result.length}")
            result
        } catch (e: Exception) {
            Timber.e(e, "IOWrapper.toString - Failed to convert to string")
            throw e
        }
    }

    fun toBase64(bytes: ByteArray): String {
        Timber.d("IOWrapper.toBase64 - Converting ${bytes.size} bytes to Base64")
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun fromBase64(base64: String): ByteArray {
        Timber.d("IOWrapper.fromBase64 - Decoding Base64 string of length ${base64.length}")
        return Base64.decode(base64, Base64.DEFAULT)
    }
} 