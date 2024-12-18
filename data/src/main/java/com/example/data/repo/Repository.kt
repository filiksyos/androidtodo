package com.example.data.repo

import com.example.data.DashboardItems
import com.example.data.Utils.YouTubeLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val resourceProvider: ResourceProvider
) {

    suspend fun getReadingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Companion.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_reading_lesson_card",
                        resourceProvider.getString(R.string.reading),
                        resourceProvider.getString(R.string.lesson),
                        resourceProvider.getColor(R.color.blue_500),
                        resourceProvider.getQuery(YouTubeLink.ReadingLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        resourceProvider.getString(R.string.reading),
                        resourceProvider.getString(R.string.test),
                        resourceProvider.getColor(R.color.orange_800),
                        resourceProvider.getQuery(YouTubeLink.ReadingTestLink)
                    )
                )
            )
        } catch (e: Exception) {
            Result.Companion.failure(e)
        }
    }

    suspend fun getListeningItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Companion.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_test",
                        resourceProvider.getString(R.string.listening),
                        resourceProvider.getString(R.string.lesson),
                        resourceProvider.getColor(R.color.red_500),
                        resourceProvider.getQuery(YouTubeLink.ListeningLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_test",
                        resourceProvider.getString(R.string.listening),
                        resourceProvider.getString(R.string.test),
                        resourceProvider.getColor(R.color.pink_400),
                        resourceProvider.getQuery(YouTubeLink.ListeningTestLink)
                    )
                )
            )
        } catch (e: Exception) {
            Result.Companion.failure(e)
        }
    }

    suspend fun getWritingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        try {
            Result.Companion.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        resourceProvider.getString(R.string.writing),
                        resourceProvider.getString(R.string.lesson),
                        resourceProvider.getColor(R.color.purple_400),
                        resourceProvider.getQuery(YouTubeLink.WritingLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        resourceProvider.getString(R.string.writing),
                        resourceProvider.getString(R.string.writing_task_1),
                        resourceProvider.getColor(R.color.orange_800),
                        resourceProvider.getQuery(YouTubeLink.WritingTask1Link)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        resourceProvider.getString(R.string.writing),
                        resourceProvider.getString(R.string.writing_task_2),
                        resourceProvider.getColor(R.color.orange_800),
                        resourceProvider.getQuery(YouTubeLink.WritingTask2Link)
                    )
                )
            )
        } catch (e: Exception) {
            Result.Companion.failure(e)
        }
    }

    suspend fun getSpeakingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        try {
            Result.Companion.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_speaking_image_transparent_background",
                        resourceProvider.getString(R.string.speaking),
                        resourceProvider.getString(R.string.lesson),
                        resourceProvider.getColor(R.color.green_400),
                        resourceProvider.getQuery(YouTubeLink.SpeakingLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_image_transparent_background",
                        resourceProvider.getString(R.string.speaking),
                        resourceProvider.getString(R.string.test),
                        resourceProvider.getColor(R.color.blue_500),
                        resourceProvider.getQuery(YouTubeLink.SpeakingTestLink)
                    )
                )
            )
        } catch (e: Exception) {
            Result.Companion.failure(e)
        }
    }
}




