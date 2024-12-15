package com.wolfbytetechnologies.ielts.Domain

import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.data.repo.Repository

class GetDashboardItemsUseCase(private val repository: Repository) {

    fun getDashboardItems(category: DashboardCategory): List<DashboardItems> {
        return when (category) {
            DashboardCategory.READING -> repository.getReadingItems()
            DashboardCategory.LISTENING -> repository.getListeningItems()
            DashboardCategory.WRITING -> repository.getWritingItems()
            DashboardCategory.SPEAKING -> repository.getSpeakingItems()
        }
    }
}
