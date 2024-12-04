package com.wolfbytetechnologies.ielts.DI

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
}



