package com.wolfbytetechnologies.ielts.DI


import com.wolfbytetechnologies.ielts.data.repo.Repository
import com.wolfbytetechnologies.ielts.data.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.data.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.ui.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.Domain.GetDashboardItemsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    // ResourceProvider
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }

    // Repository
    single { Repository(get()) }

    // Use Case
    factory { GetDashboardItemsUseCase(get()) }

    // ViewModel
    viewModel { DashboardViewModel(get()) }
}







