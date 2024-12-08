package com.abdulhaseeb.ielts_practice_app.RepositoryTest

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


//This area is commented out because The app doesn't use networking yet.
/*
@ExperimentalCoroutinesApi
class QuestionRepositoryTest {

    private val mockDao = mockk<QuestionDao>()
    private val mockNetworkRepo = mockk<NetworkRepository>()
    private lateinit var questionRepository: QuestionRepository

    @Before
    fun setup() {
        questionRepository = QuestionRepository(mockDao, mockNetworkRepo)
    }

    @Test
    fun `test getQuestions - returns from cache`() = runBlockingTest {
        val mockQuestions = listOf(
            QuestionEntity(1, "type", "topic", "questionType", "title", "content")
        )
        coEvery { mockDao.getAllQuestions() } returns mockQuestions

        val result = questionRepository.getQuestions()

        assertEquals(1, result.size)
        assertEquals("type", result.first().type)
    }

    @Test
    fun `test getQuestions - fetches from API and caches`() = runBlockingTest {
        val mockApiResponse = ApiResponse.Success(
            listOf(Questions(1, "type", "topic", "questionType", "title", "content"))
        )
        val mockQuestions = mockApiResponse.data.map {
            QuestionEntity(it.id, it.type, it.topic, it.questionType, it.title, it.content)
        }

        coEvery { mockDao.getAllQuestions() } returns emptyList()
        coEvery { mockNetworkRepo.getQuestions() } returns mockApiResponse
        coJustRun { mockDao.insertQuestions(mockQuestions) }

        val result = questionRepository.getQuestions()

        assertEquals(1, result.size)
        assertEquals("type", result.first().type)
        coVerify { mockDao.insertQuestions(mockQuestions) }
    }
}
*/
