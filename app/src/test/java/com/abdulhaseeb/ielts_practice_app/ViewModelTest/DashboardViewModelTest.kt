package com.abdulhaseeb.ielts_practice_app.ViewModelTest

import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.CategorizeDashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import io.mockk.every
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    private val mockRepo = mockk<MainDashboardItemsRepo>()
    private val categorizeItems = CategorizeDashboardItems()
    private lateinit var dashboardViewModel: DashboardViewModel

    @Before
    fun setup() {
        dashboardViewModel = DashboardViewModel(mockRepo, categorizeItems)
    }

    @Test
    fun `test loadDashboardItems - happy path`() = runBlockingTest {
        val mockItems = listOf(
            DashboardItems(itemText = "Reading"),
            DashboardItems(itemText = "Listening"),
            DashboardItems(itemText = "Writing"),
            DashboardItems(itemText = "Speaking")
        )
        every { mockRepo.getDashboardItems() } returns mockItems

        dashboardViewModel.loadDashboardItems()

        val readingItems = dashboardViewModel.readingItems.value
        assertEquals(1, readingItems?.size)
        assertEquals("Reading", readingItems?.first()?.itemText)
    }

    @Test
    fun `test loadDashboardItems - empty response`() = runBlockingTest {
        every { mockRepo.getDashboardItems() } returns emptyList()

        dashboardViewModel.loadDashboardItems()

        assertTrue(dashboardViewModel.readingItems.value.isNullOrEmpty())
    }

}
