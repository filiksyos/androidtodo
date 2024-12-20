package com.example.data

import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryTest {

    private val repository = Repository()

    @Test
    fun `getDashboardItems returns correct data for READING`() {
        // Act
        val result = repository.getDashboardItems(DashboardCategory.READING)

        // Assert
        assertEquals("Reading", result[0].itemText)
        assertEquals("Lesson", result[0].cardType)
    }

    @Test
    fun `getDashboardItems returns correct data for LISTENING`() {
        // Act
        val result = repository.getDashboardItems(DashboardCategory.LISTENING)

        // Assert
        assertEquals("Listening", result[0].itemText)
        assertEquals("Lesson", result[0].cardType)
    }
}
