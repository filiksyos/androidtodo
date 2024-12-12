package com.wolfbytetechnologies.ielts.data.Utils

import android.util.Log
import com.wolfbytetechnologies.ielts.BuildConfig

object Logger {
    private val ENABLE_LOGS = BuildConfig.DEBUG

    fun logDebug(tag: String, message: String) {
        if (ENABLE_LOGS) Log.d(tag, message)
    }

    fun logError(tag: String, message: String) {
        if (ENABLE_LOGS) Log.e(tag, message)
    }
}
