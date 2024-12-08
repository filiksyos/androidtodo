package com.abdulhaseeb.ielts_practice_app.RepositoryTest

import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.ResourceProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainDashboardItemsRepoTest {

    private lateinit var repo: MainDashboardItemsRepo
    private lateinit var fakeResourceProvider: ResourceProvider

    @Before
    fun setup() {
        // Using a FakeResourceProvider for testing
        fakeResourceProvider = FakeResourceProvider()
        repo = MainDashboardItemsRepo(fakeResourceProvider)
    }

    @Test
    fun `test getDashboardItems returns correct items`() {
        val dashboardItems = repo.getDashboardItems()

        // Verifying item count and specific details
        assertEquals(9, dashboardItems.size)
        assertEquals("Fake Reading", dashboardItems[0].itemText)
        assertEquals("Fake Writing", dashboardItems[2].itemText)
    }
}

