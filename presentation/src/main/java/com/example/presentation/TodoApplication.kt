package com.example.presentation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.ChromiaRepository
import com.example.data.di.dataModule
import com.example.domain.CreateTodoUseCase
import com.example.domain.GetTodosUseCase
import com.example.domain.UpdateTodoUseCase
import com.example.presentation.viewModel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class TodoApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val presentationModule = module {
        // UseCases
        single { GetTodosUseCase(get()) }
        single { CreateTodoUseCase(get()) }
        single { UpdateTodoUseCase(get()) }

        // ViewModels
        viewModel { TodoViewModel(get(), get(), get()) }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber for logging
        Timber.plant(Timber.DebugTree())
        
        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@TodoApplication)
            modules(listOf(
                dataModule,
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