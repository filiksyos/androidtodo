package com.example.domain

import com.example.data.DashboardItems
import com.example.data.Repository

class GetDashboardItemsUseCase(private val repository: Repository) {

    operator fun invoke(category: DashboardCategory): List<DashboardItems> {
        return when (category) {
            DashboardCategory.READING -> repository.getReadingItems()
            DashboardCategory.LISTENING -> repository.getListeningItems()
            DashboardCategory.WRITING -> repository.getWritingItems()
            DashboardCategory.SPEAKING -> repository.getSpeakingItems()
        }
    }
}


