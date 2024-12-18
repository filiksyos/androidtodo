package com.example.domain

import com.example.data.DashboardItems
import com.example.data.Repository

sealed class DashboardResult {
    data class Success(val data: kotlin.Result<List<DashboardItems>>) : DashboardResult()
    data class Error(val exception: Throwable) : DashboardResult()
    object Loading : DashboardResult()
}

class GetDashboardItemsUseCase(private val repository: Repository) {
    suspend fun execute(category: DashboardCategory): DashboardResult {
        return try {
            val items = when (category) {
                DashboardCategory.READING -> repository.getReadingItems()
                DashboardCategory.LISTENING -> repository.getListeningItems()
                DashboardCategory.WRITING -> repository.getWritingItems()
                DashboardCategory.SPEAKING -> repository.getSpeakingItems()
            }
            DashboardResult.Success(items)
        } catch (e: Exception) {
            DashboardResult.Error(e)
        }
    }
}


