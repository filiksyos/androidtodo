package com.wolfbytetechnologies.ielts.data.repo

import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.data.Utils.YouTubeLink
import com.wolfbytetechnologies.ielts.data.DashboardItems

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val resourceProvider: ResourceProvider
) {

    suspend fun getReadingItems(): List<DashboardItems> = withContext(Dispatchers.IO) {
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
    }

    suspend fun getListeningItems(): List<DashboardItems> = withContext(Dispatchers.IO) {
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
    }

    suspend fun getWritingItems(): List<DashboardItems> = withContext(Dispatchers.IO) {
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
    }

    suspend fun getSpeakingItems(): List<DashboardItems> = withContext(Dispatchers.IO) {
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
    }
}



