package com.example.data.Utils

import android.util.Log

object Logger {

    private val ENABLE_LOGS = true

    fun logDebug(tag: String, message: String) {
        if (ENABLE_LOGS) Log.d(tag, message)
    }

    fun logError(tag: String, message: String) {
        if (ENABLE_LOGS) Log.e(tag, message)
    }
}
