package com.example.data.di

import com.example.data.ChromiaRepository
import org.koin.dsl.module

val dataModule = module {
    single { ChromiaRepository.getInstance() }
} 