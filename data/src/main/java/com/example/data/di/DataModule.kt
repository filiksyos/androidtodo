package com.example.data.di

import com.example.data.ChromiaRepository
import com.example.data.TodoRepository
import org.koin.dsl.module

val dataModule = module {
    single<TodoRepository> { ChromiaRepository.getInstance() }
} 