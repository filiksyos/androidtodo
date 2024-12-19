package com.example.presentation

import android.app.Application
import com.example.presentation.DI.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseClass : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseClass) // Set the Android context
            androidLogger(Level.DEBUG) // Add logging
            modules(appModule) // Load the Koin module
        }

    }
}
