package com.example.presentation

import com.example.data.Repository
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryTest {

    private val repository = Repository()

    @Test
    fun `getReadingItems returns correct data`() {
        // Act
        val result = repository.getReadingItems()

        // Assert
        assertEquals("Reading", result[0].itemText)
        assertEquals("Lesson", result[0].cardType)
    }
}
