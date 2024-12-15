package com.wolfbytetechnologies.ielts.DI


import com.wolfbytetechnologies.ielts.Domain.DashboardCategory
import com.wolfbytetechnologies.ielts.data.repo.Repository
import com.wolfbytetechnologies.ielts.data.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.data.repo.ResourceProviderImpl
import com.wolfbytetechnologies.ielts.ui.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.Domain.GetDashboardItemsUseCase
import com.wolfbytetechnologies.ielts.ui.Fragment.DashboardFragment
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    // ResourceProvider
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }

    // Repository: Use single for shared instances
    single { Repository(get()) }

    // UseCase: Scoped per ViewModel or fragment
    factory { GetDashboardItemsUseCase(get()) }

    // ViewModel: Scoped for fragments
    scope<DashboardFragment> {
        viewModel { DashboardViewModel(get()) }
    }
}







