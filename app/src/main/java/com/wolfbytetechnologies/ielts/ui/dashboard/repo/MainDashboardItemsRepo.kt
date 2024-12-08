package com.wolfbytetechnologies.ielts.ui.dashboard.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems

class MainDashboardItemsRepo(
    private val resourceProvider: ResourceProvider
) {

    fun getDashboardItems(): List<DashboardItems> {
        return listOf(
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_reading_lesson_card),
                resourceProvider.getString(R.string.reading),
                resourceProvider.getString(R.string.lesson),
                resourceProvider.getColor(R.color.blue_500)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_test_card),
                resourceProvider.getString(R.string.reading),
                resourceProvider.getString(R.string.test),
                resourceProvider.getColor(R.color.orange_800)
            ),

            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_test_card),
                resourceProvider.getString(R.string.writing),
                resourceProvider.getString(R.string.lesson),
                resourceProvider.getColor(R.color.purple_400)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_test_card),
                resourceProvider.getString(R.string.writing),
                resourceProvider.getString(R.string.writing_task_1),
                resourceProvider.getColor(R.color.orange_800)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_test_card),
                resourceProvider.getString(R.string.writing),
                resourceProvider.getString(R.string.writing_task_2),
                resourceProvider.getColor(R.color.orange_800)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_speaking_image_transparent_background),
                resourceProvider.getString(R.string.speaking),
                resourceProvider.getString(R.string.lesson),
                resourceProvider.getColor(R.color.green_400)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_listening_image_transparent_background),
                resourceProvider.getString(R.string.speaking),
                resourceProvider.getString(R.string.test),
                resourceProvider.getColor(R.color.blue_500)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_listening_test),
                resourceProvider.getString(R.string.listening),
                resourceProvider.getString(R.string.lesson),
                resourceProvider.getColor(R.color.red_500)
            ),
            DashboardItems(
                resourceProvider.getDrawable(R.drawable.ic_listening_test),
                resourceProvider.getString(R.string.listening),
                resourceProvider.getString(R.string.test),
                resourceProvider.getColor(R.color.pink_400)
            )

        )
    }
}


