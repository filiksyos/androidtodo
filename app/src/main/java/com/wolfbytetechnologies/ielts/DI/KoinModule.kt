package com.wolfbytetechnologies.ielts.DI

import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // Provide the repository as a singleton
    single { MainDashboardItemsRepo(androidContext()) }

    // Provide the ViewModel and inject the repository dependency
    viewModel { DashboardViewModel(get()) }
}

