package com.wolfbytetechnologies.ielts.viewModel

import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.repo.Repository

class GetDashboardItemsUseCase(private val repository: Repository) {
    fun getReadingItems(): List<DashboardItems> = repository.getReadingItems()
    fun getListeningItems(): List<DashboardItems> = repository.getListeningItems()
    fun getWritingItems(): List<DashboardItems> = repository.getWritingItems()
    fun getSpeakingItems(): List<DashboardItems> = repository.getSpeakingItems()
}
