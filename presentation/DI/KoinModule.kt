import com.example.data.repo.Repository
import com.example.data.repo.ResourceProviderImpl
import com.example.presentation.viewModel.DashboardViewModel
import com.example.domain.GetDashboardItemsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    // ResourceProvider
    single<com.example.data.repo.ResourceProvider> {
        com.example.data.repo.ResourceProviderImpl(
            androidContext()
        )
    }

    // Repository: Singleton for shared instance
    single { com.example.data.repo.Repository(get()) }

    // UseCase: Factory ensures new instance for each ViewModel
    factory { com.example.domain.GetDashboardItemsUseCase(get()) }

    // ViewModel: Globally available
    viewModel { com.example.presentation.viewModel.DashboardViewModel(get()) }
}








