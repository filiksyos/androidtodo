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
        
        // Comprehensive Commons IO Diagnostics
        Timber.i("=== Starting Commons IO Diagnostics ===")
        
        try {
            // 1. Check direct Commons IO
            val commonsIoClass = Class.forName("org.apache.commons.io.IOUtils")
            Timber.i("✓ Successfully loaded Commons IO class")
            
            // 2. Check Postchain's usage of Commons IO
            val postchainClass = Class.forName("net.postchain.client.core.PostchainClient")
            Timber.i("✓ Successfully loaded Postchain class")
            
            // 3. Detailed class loading info
            Timber.i("=== Class Loading Details ===")
            Timber.i("Commons IO ClassLoader: ${commonsIoClass.classLoader}")
            Timber.i("Postchain ClassLoader: ${postchainClass.classLoader}")
            Timber.i("Current ClassLoader: ${this.javaClass.classLoader}")
            
            // 4. Check if Commons IO is loaded from Postchain's JAR
            val codeSource = commonsIoClass.protectionDomain?.codeSource
            Timber.i("Commons IO loaded from: ${codeSource?.location}")
            
            // 5. Check package info with source
            val pkg = commonsIoClass.getPackage()
            Timber.i("=== Package Details ===")
            Timber.i("Implementation Version: ${pkg?.implementationVersion ?: "null"} from ${pkg?.implementationVendor ?: "unknown"}")
            Timber.i("Specification Version: ${pkg?.specificationVersion ?: "null"} from ${pkg?.specificationVendor ?: "unknown"}")
            
            // 6. Try to load readAllBytes method
            try {
                val readAllBytesMethod = commonsIoClass.getMethod("readAllBytes", java.io.InputStream::class.java)
                Timber.i("✓ readAllBytes method found in ${readAllBytesMethod.declaringClass.name}")
                
                // Check if method is from repackaged class
                val methodSource = readAllBytesMethod.declaringClass.protectionDomain?.codeSource
                Timber.i("Method loaded from: ${methodSource?.location}")
            } catch (e: NoSuchMethodException) {
                Timber.e("✗ readAllBytes method not found: ${e.message}")
            }
            
            // 7. Check all loaded Commons IO classes
            val loadedClasses = (this.javaClass.classLoader as? dalvik.system.BaseDexClassLoader)
                ?.toString()
                ?.split("zip:")
                ?.filter { it.contains("commons-io") }
            Timber.i("=== Loaded Commons IO Related Files ===")
            loadedClasses?.forEach { Timber.i("Found: $it") }
            
        } catch (e: Exception) {
            Timber.e(e, "Error during Commons IO diagnostics")
        }
        
        Timber.i("=== Commons IO Diagnostics Complete ===")
        
        // Continue with normal initialization
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