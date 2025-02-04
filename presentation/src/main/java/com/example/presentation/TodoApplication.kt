package com.example.presentation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.ChromiaRepository
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.presentation.di.presentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TodoApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber for logging
        Timber.plant(Timber.DebugTree())
        
        // Log commons-io version
        try {
            val commonsIoVersion = Class.forName("org.apache.commons.io.IOUtils")
                .getPackage()
                .implementationVersion
            Timber.i("Commons-IO Runtime Version: $commonsIoVersion")
            
            // Additional class information
            val classLoader = Class.forName("org.apache.commons.io.IOUtils").classLoader
            Timber.i("Commons-IO ClassLoader: ${classLoader?.javaClass?.name}")
            Timber.i("Commons-IO ClassLoader Location: ${classLoader?.toString()}")
        } catch (e: Exception) {
            Timber.e(e, "Failed to get Commons-IO version")
        }
        
        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@TodoApplication)
            modules(listOf(
                dataModule,
                domainModule,
                presentationModule
            ))
        }
        
        // Initialize ChromiaRepository
        applicationScope.launch {
            try {
                ChromiaRepository.getInstance().initialize(applicationContext)
                Timber.d("ChromiaRepository initialized successfully")
            } catch (e: Exception) {
                Timber.e(e, "Failed to initialize ChromiaRepository")
            }
        }
    }
} 