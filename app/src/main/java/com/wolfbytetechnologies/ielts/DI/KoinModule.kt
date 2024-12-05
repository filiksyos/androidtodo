package com.wolfbytetechnologies.ielts.DI

import com.wolfbytetechnologies.ielts.Networking.IELTSApiService
import com.wolfbytetechnologies.ielts.Networking.NetworkModule
import com.wolfbytetechnologies.ielts.Networking.NetworkRepository
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.AdapterProvider
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.AdapterProviderImpl
import com.wolfbytetechnologies.ielts.Utils.InternetUtility
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }
    single { MainDashboardItemsRepo(get()) }
    viewModel { DashboardViewModel(get()) }
    single { InternetUtility(androidContext()) }
    single<AdapterProvider> { AdapterProviderImpl() }
    single { NetworkModule.retrofit.create(IELTSApiService::class.java) }
    single { NetworkRepository(get()) }
}



