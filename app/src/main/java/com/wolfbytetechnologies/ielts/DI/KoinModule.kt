package com.wolfbytetechnologies.ielts.DI


import com.wolfbytetechnologies.ielts.repo.Repository
import com.wolfbytetechnologies.ielts.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.viewModel.GetDashboardItemsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.factoryOf


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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







