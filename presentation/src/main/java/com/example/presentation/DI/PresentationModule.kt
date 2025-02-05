package com.example.presentation.di

import com.example.presentation.viewModel.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    // ViewModel binding
    viewModel { 
        TodoViewModel(
            getTodosUseCase = get(),
            createTodoUseCase = get(),
            updateTodoUseCase = get(),
            deleteTodoUseCase = get(),
            toggleTodoUseCase = get()
        ) 
    }
} 