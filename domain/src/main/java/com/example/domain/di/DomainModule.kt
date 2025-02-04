package com.example.domain.di

import com.example.domain.usecase.todo.*
import org.koin.dsl.module

val domainModule = module {
    // Todo use cases
    factory { GetTodosUseCase(get()) }
    factory { CreateTodoUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { DeleteTodoUseCase(get()) }
    factory { ToggleTodoUseCase(get()) }
} 