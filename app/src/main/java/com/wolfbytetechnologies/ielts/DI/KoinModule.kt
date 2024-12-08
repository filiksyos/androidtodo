package com.wolfbytetechnologies.ielts.DI

import com.wolfbytetechnologies.ielts.Networking.IELTSApiService
import com.wolfbytetechnologies.ielts.Networking.NetworkModule
import com.wolfbytetechnologies.ielts.Networking.NetworkRepository
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.AdapterProvider
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.AdapterProviderImpl
import com.wolfbytetechnologies.ielts.Utils.InternetUtility
import com.wolfbytetechnologies.ielts.Utils.NetworkChecker
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.CategorizeDashboardItemsUseCase
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    // Providing the use case
    single { CategorizeDashboardItemsUseCase() }

    // Providing the ResourceProvider
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }

    // Providing the MainDashboardItemsRepo
    single { MainDashboardItemsRepo(get()) }

    // Providing the DashboardViewModel and injecting the use case properly
    viewModel {
        DashboardViewModel(
            repo = get(),
            categorizeUseCase = get()
        )
    }

    // Providing InternetUtility
    single { InternetUtility(androidContext()) }

    single<NetworkChecker> { InternetUtility(androidContext()) }

    // Providing AdapterProvider implementation
    single<AdapterProvider> { AdapterProviderImpl() }

    // Providing the network layer
    single { NetworkModule.retrofit.create(IELTSApiService::class.java) }
    single { NetworkRepository(get()) }
}




