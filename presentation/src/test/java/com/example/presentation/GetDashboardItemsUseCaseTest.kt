package com.example.presentation

import com.example.data.DashboardItems
import com.example.data.Repository
import com.example.domain.DashboardCategory
import com.example.domain.GetDashboardItemsUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class GetDashboardItemsUseCaseTest {

    private val repository = mockk<Repository>()
    private val useCase = GetDashboardItemsUseCase(repository)

    @Test
    fun `invoke returns correct items for READING`() {
        // Arrange
        val readingItems = listOf(
            DashboardItems("uri1", "Reading", "Lesson"),
            DashboardItems("uri2", "Reading", "Test")
        )
        every { repository.getReadingItems() } returns readingItems

        // Act
        val result = useCase.invoke(DashboardCategory.READING)

        // Assert
        assertEquals(readingItems, result)
    }
}
