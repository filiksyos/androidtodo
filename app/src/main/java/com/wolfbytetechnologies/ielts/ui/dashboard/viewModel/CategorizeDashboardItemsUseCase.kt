package com.wolfbytetechnologies.ielts.ui.dashboard.viewModel

import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems

class CategorizeDashboardItemsUseCase {
    fun invoke(items: List<DashboardItems>): CategorizedDashboardItems {
        return CategorizedDashboardItems(
            readingItems = items.filter { it.itemText == "Reading" },
            listeningItems = items.filter { it.itemText == "Listening" },
            writingItems = items.filter { it.itemText == "Writing" },
            speakingItems = items.filter { it.itemText == "Speaking" }
        )
    }
}

data class CategorizedDashboardItems(
    val readingItems: List<DashboardItems>,
    val listeningItems: List<DashboardItems>,
    val writingItems: List<DashboardItems>,
    val speakingItems: List<DashboardItems>
)
