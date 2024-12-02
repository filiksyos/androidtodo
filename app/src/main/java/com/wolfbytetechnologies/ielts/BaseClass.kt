package com.wolfbytetechnologies.ielts

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.wolfbytetechnologies.ielts.DI.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseClass : Application() {

    companion object {
        fun prefEditor(context: Context): SharedPreferences? {
            val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
            return sharedPreferences
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Start Koin and load modules
        startKoin {
            androidContext(this@BaseClass) // Set the Android context
            modules(appModule) // Load the Koin module
        }
    }
}
