package com.example.data

import com.example.data.Utils.YouTubeLink

class Repository : RepositoryInterface {
    override fun getDashboardItems(category: DashboardCategory): List<DashboardItems> {
        return listOf(
            DashboardItems(category.iconUri, category.title, "Lesson", category.color, YouTubeLink.getLink(category.title + " Lesson")),
            DashboardItems(category.iconUri, category.title, "Test", category.color, YouTubeLink.getLink(category.title + " Test"))
        )
    }
}
