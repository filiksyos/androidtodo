package com.example.data.util

import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import org.apache.commons.io.input.BoundedInputStream
import timber.log.Timber

fun InputStream.safeReadAllBytes(): ByteArray {
    Timber.d("IOExtensions.safeReadAllBytes - ENTRY POINT")
    Timber.d("IOExtensions.safeReadAllBytes - Stream type: ${this.javaClass.name}")
    Timber.d("IOExtensions.safeReadAllBytes - Stream hash: ${System.identityHashCode(this)}")
    return try {
        val result = IOWrapper.readAllBytes(this)
        Timber.d("IOExtensions.safeReadAllBytes - Successfully delegated to IOWrapper")
        result
    } catch (e: Exception) {
        Timber.e(e, "IOExtensions.safeReadAllBytes - Failed to delegate to IOWrapper")
        throw e
    } finally {
        Timber.d("IOExtensions.safeReadAllBytes - EXIT POINT")
    }
}

fun OutputStream.safeWriteBytes(bytes: ByteArray) {
    Timber.d("IOExtensions.safeWriteBytes - Delegating to IOWrapper")
    IOWrapper.writeBytes(this, bytes)
}

fun InputStream.safeToString(charset: Charset = Charsets.UTF_8): String {
    Timber.d("IOExtensions.safeToString - Delegating to IOWrapper")
    return IOWrapper.toString(this, charset)
}

// Extension specifically for BoundedInputStream
fun BoundedInputStream.readAllBytes(): ByteArray {
    Timber.d("IOExtensions.readAllBytes (BoundedInputStream) - ENTRY POINT")
    Timber.d("IOExtensions.readAllBytes - BoundedInputStream hash: ${System.identityHashCode(this)}")
    return try {
        val result = IOWrapper.readAllBytes(this)
        Timber.d("IOExtensions.readAllBytes - Successfully delegated to IOWrapper")
        result
    } catch (e: Exception) {
        Timber.e(e, "IOExtensions.readAllBytes - Failed to delegate to IOWrapper")
        Timber.e("IOExtensions.readAllBytes - Error type: ${e.javaClass.name}")
        Timber.e("IOExtensions.readAllBytes - Error message: ${e.message}")
        throw e
    } finally {
        Timber.d("IOExtensions.readAllBytes - EXIT POINT")
    }
} 