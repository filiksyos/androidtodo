package com.example.data.repo

import com.example.data.DashboardItems
import com.example.data.Utils.YouTubeLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.domain.ResourceProvider


class Repository(
    private val resourceProvider: ResourceProvider
) {

    suspend fun getReadingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Companion.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_reading_lesson_card",
                        ResourceProvider.getString(R.string.reading),
                        ResourceProvider.getString(R.string.lesson),
                        ResourceProvider.getColor(R.color.blue_500),
                        ResourceProvider.getQuery(YouTubeLink.ReadingLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        ResourceProvider.getString(R.string.reading),
                        ResourceProvider.getString(R.string.test),
                        ResourceProvider.getColor(R.color.orange_800),
                        ResourceProvider.getQuery(YouTubeLink.ReadingTestLink)
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
                        ResourceProvider.getString(R.string.listening),
                        ResourceProvider.getString(R.string.lesson),
                        ResourceProvider.getColor(R.color.red_500),
                        ResourceProvider.getQuery(YouTubeLink.ListeningLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_test",
                        ResourceProvider.getString(R.string.listening),
                        ResourceProvider.getString(R.string.test),
                        ResourceProvider.getColor(R.color.pink_400),
                        ResourceProvider.getQuery(YouTubeLink.ListeningTestLink)
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
                        ResourceProvider.getString(R.string.writing),
                        ResourceProvider.getString(R.string.lesson),
                        ResourceProvider.getColor(R.color.purple_400),
                        ResourceProvider.getQuery(YouTubeLink.WritingLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        ResourceProvider.getString(R.string.writing),
                        ResourceProvider.getString(R.string.writing_task_1),
                        ResourceProvider.getColor(R.color.orange_800),
                        ResourceProvider.getQuery(YouTubeLink.WritingTask1Link)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        ResourceProvider.getString(R.string.writing),
                        ResourceProvider.getString(R.string.writing_task_2),
                        ResourceProvider.getColor(R.color.orange_800),
                        ResourceProvider.getQuery(YouTubeLink.WritingTask2Link)
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
                        ResourceProvider.getString(R.string.speaking),
                        ResourceProvider.getString(R.string.lesson),
                        ResourceProvider.getColor(R.color.green_400),
                        ResourceProvider.getQuery(YouTubeLink.SpeakingLessonLink)
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_image_transparent_background",
                        ResourceProvider.getString(R.string.speaking),
                        ResourceProvider.getString(R.string.test),
                        ResourceProvider.getColor(R.color.blue_500),
                        ResourceProvider.getQuery(YouTubeLink.SpeakingTestLink)
                    )
                )
            )
        } catch (e: Exception) {
            Result.Companion.failure(e)
        }
    }
}




