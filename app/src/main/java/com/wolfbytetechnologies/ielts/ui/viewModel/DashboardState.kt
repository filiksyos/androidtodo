package com.wolfbytetechnologies.ielts.ui.viewModel

import androidx.paging.PagingData
import com.wolfbytetechnologies.ielts.data.DashboardItems

sealed class DashboardState {
    object Loading : DashboardState()
    object Empty : DashboardState()
    data class Success(val data: PagingData<DashboardItems>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

