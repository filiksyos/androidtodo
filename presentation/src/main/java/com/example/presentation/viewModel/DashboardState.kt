package com.example.presentation.viewModel

import androidx.paging.PagingData
import com.example.data.DashboardItems

sealed class DashboardState {
    object Loading : DashboardState()
    object Empty : DashboardState()
    data class Success(val data: PagingData<DashboardItems>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}


