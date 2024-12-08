package com.abdulhaseeb.ielts_practice_app.ViewModelTest

import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.CategorizeDashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import org.junit.Assert.assertEquals
import org.junit.Test

class CategorizeDashboardItemsTest {

    private val useCase = CategorizeDashboardItems()

    @Test
    fun `test categorization of dashboard items`() {
        val items = listOf(
            DashboardItems(itemText = "Reading"),
            DashboardItems(itemText = "Listening"),
            DashboardItems(itemText = "Writing"),
            DashboardItems(itemText = "Speaking")
        )

        val categorized = useCase.invoke(items)

        assertEquals(1, categorized.readingItems.size)
        assertEquals(1, categorized.listeningItems.size)
        assertEquals(1, categorized.writingItems.size)
        assertEquals(1, categorized.speakingItems.size)
    }
}
