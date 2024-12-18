package com.example.data

import com.example.data.Utils.YouTubeLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    suspend fun getReadingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_reading_lesson_card",
                        "Reading",  // Hardcoded string
                        "Lesson",   // Hardcoded string
                        0xFF2196F3.toInt(), // Hardcoded color (blue_500)
                        YouTubeLink.ReadingLessonLink
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        "Reading",  // Hardcoded string
                        "Test",     // Hardcoded string
                        0xFFFF9800.toInt(), // Hardcoded color (orange_800)
                        YouTubeLink.ReadingTestLink
                    )
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getListeningItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_test",
                        "Listening",  // Hardcoded string
                        "Lesson",     // Hardcoded string
                        0xFFF44336.toInt(), // Hardcoded color (red_500)
                        YouTubeLink.ListeningLessonLink
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_test",
                        "Listening",  // Hardcoded string
                        "Test",       // Hardcoded string
                        0xFFFFC0CB.toInt(), // Hardcoded color (pink_400)
                        YouTubeLink.ListeningTestLink
                    )
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getWritingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        "Writing",    // Hardcoded string
                        "Lesson",     // Hardcoded string
                        0xFF9C27B0.toInt(), // Hardcoded color (purple_400)
                        YouTubeLink.WritingLessonLink
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        "Writing",    // Hardcoded string
                        "Task 1",     // Hardcoded string
                        0xFFFF9800.toInt(), // Hardcoded color (orange_800)
                        YouTubeLink.WritingTask1Link
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_test_card",
                        "Writing",    // Hardcoded string
                        "Task 2",     // Hardcoded string
                        0xFFFF9800.toInt(), // Hardcoded color (orange_800)
                        YouTubeLink.WritingTask2Link
                    )
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSpeakingItems(): Result<List<DashboardItems>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.success(
                listOf(
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_speaking_image_transparent_background",
                        "Speaking",   // Hardcoded string
                        "Lesson",     // Hardcoded string
                        0xFF4CAF50.toInt(), // Hardcoded color (green_400)
                        YouTubeLink.SpeakingLessonLink
                    ),
                    DashboardItems(
                        "android.resource://com.wolfbytetechnologies.ielts/drawable/ic_listening_image_transparent_background",
                        "Speaking",   // Hardcoded string
                        "Test",       // Hardcoded string
                        0xFF2196F3.toInt(), // Hardcoded color (blue_500)
                        YouTubeLink.SpeakingTestLink
                    )
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
