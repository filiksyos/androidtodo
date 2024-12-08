package com.wolfbytetechnologies.ielts.Utils

import android.content.Context
import android.net.ConnectivityManager

class InternetUtility(private val context: Context) : NetworkChecker {
    override val isConnected: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }
}

