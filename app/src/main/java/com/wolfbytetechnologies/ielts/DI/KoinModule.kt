package com.wolfbytetechnologies.ielts.DI


import com.wolfbytetechnologies.ielts.repo.Repository
import com.wolfbytetechnologies.ielts.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.viewModel.GetDashboardItemsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // Provide ResourceProvider
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }

    // Provide Repository
    single { Repository(get()) }

    // Provide UseCase
    single { GetDashboardItemsUseCase(get()) }

    // Provide ViewModel
    viewModel { DashboardViewModel(get()) }

}




