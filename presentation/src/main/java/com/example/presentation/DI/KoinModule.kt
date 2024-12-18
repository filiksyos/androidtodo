package com.example.presentation.DI

import com.example.data.Repository
import com.example.presentation.viewModel.DashboardViewModel
import com.example.domain.GetDashboardItemsUseCase
import org.koin.dsl.module


import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // Repository: Singleton for shared instance
    single { Repository() }

    // UseCase: Factory ensures new instance for each ViewModel
    factory { GetDashboardItemsUseCase(get()) }

    // ViewModel: Globally available
    viewModel { DashboardViewModel(get()) }
}








