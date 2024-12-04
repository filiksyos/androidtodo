package com.abdulhaseeb.ielts_practice_app

import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainDashboardItemsRepoTest {

    private lateinit var repo: MainDashboardItemsRepo

    @Before
    fun setup() {
        // Inject the fake provider into the repository
        val fakeResourceProvider = FakeResourceProvider()
        repo = MainDashboardItemsRepo(fakeResourceProvider)
    }

    @Test
    fun testGetDashboardItems() {
        // Fetch dashboard items
        val items = repo.getDashboardItems()

        // Verify that the fake values are returned
        assertEquals("Fake Reading", items[0].itemText)
        assertEquals("Fake Listening", items[1].itemText)
    }
}
