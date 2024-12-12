package com.wolfbytetechnologies.ielts.Domain

import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.data.repo.Repository

class GetDashboardItemsUseCase(private val repository: Repository) {
    fun getReadingItems(page: Int, size: Int): List<DashboardItems> = repository.getReadingItems()
    fun getListeningItems(page: Int, size: Int): List<DashboardItems> = repository.getListeningItems()
    fun getWritingItems(page: Int, size: Int): List<DashboardItems> = repository.getWritingItems()
    fun getSpeakingItems(page: Int, size: Int): List<DashboardItems> = repository.getSpeakingItems()
}
