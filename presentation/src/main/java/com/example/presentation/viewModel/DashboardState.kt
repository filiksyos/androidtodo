package com.example.presentation.viewModel

import androidx.paging.PagingData

sealed class DashboardState {
    object Loading : DashboardState()
    object Empty : DashboardState()
    data class Success(val data: PagingData<com.example.data.DashboardItems>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

