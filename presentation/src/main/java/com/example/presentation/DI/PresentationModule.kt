package com.example.presentation.di

import com.example.data.TodoRepository
import com.example.data.impl.TodoRepositoryImpl
import com.example.domain.CreateTodoUseCase
import com.example.domain.GetTodosUseCase
import com.example.domain.UpdateTodoUseCase
import com.example.presentation.viewModel.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    // Repository binding
    single<TodoRepository> { TodoRepositoryImpl() }

    // Use Cases
    factory { GetTodosUseCase(get()) }
    factory { CreateTodoUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }

    // ViewModel binding
    viewModel { 
        TodoViewModel(
            getTodosUseCase = get(),
            createTodoUseCase = get(),
            updateTodoUseCase = get()
        ) 
    }
} 