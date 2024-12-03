package com.wolfbytetechnologies.ielts.ui.dashboard.viewModel

import androidx.lifecycle.ViewModel
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo

class DashboardViewModel(private val repo: MainDashboardItemsRepo) : ViewModel() {
    val dashboardItems = mutableListOf<DashboardItems>()

    fun addItemsToDashboard(items: List<DashboardItems>) {
        dashboardItems.addAll(items)
    }
}
