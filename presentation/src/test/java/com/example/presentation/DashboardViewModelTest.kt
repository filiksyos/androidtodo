package com.example.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.DashboardItems
import com.example.data.Repository
import com.example.domain.GetDashboardItemsUseCase
import com.example.domain.DashboardCategory
import com.example.presentation.viewModel.DashboardViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.text.get

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel
    private val repository = mockk<Repository>()
    private val useCase = GetDashboardItemsUseCase(repository)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }

    @Test
    fun `loadDashboardItems updates LiveData correctly`() = runTest(testDispatcher) {
        // Arrange
        val readingItems = listOf(
            DashboardItems("uri1", "Reading", "Lesson"),
            DashboardItems("uri2", "Reading", "Test")
        )
        every { repository.getReadingItems() } returns readingItems
        every { repository.getListeningItems() } returns emptyList()
        every { repository.getWritingItems() } returns emptyList()
        every { repository.getSpeakingItems() } returns emptyList()

        // Act
        viewModel.loadDashboardItems()

        // Assert
        // Assert
        viewModel.dashboardItems.observeForever { dashboardItems ->
            assertNotNull(dashboardItems)
            assertEquals(readingItems, dashboardItems[DashboardCategory.READING])
        }
    }
}
