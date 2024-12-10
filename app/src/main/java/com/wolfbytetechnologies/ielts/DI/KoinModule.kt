package com.wolfbytetechnologies.ielts.DI


import com.wolfbytetechnologies.ielts.repo.Repository
import com.wolfbytetechnologies.ielts.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.viewModel.DashboardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // Providing the ResourceProvider
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }

    // Providing the MainDashboardItemsRepo
    single { Repository(get()) }

    // Providing the DashboardViewModel and injecting the use case properly
    viewModel { DashboardViewModel(get()) }

}




