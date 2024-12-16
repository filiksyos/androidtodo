package com.wolfbytetechnologies.ielts.data

import android.graphics.Color
import com.wolfbytetechnologies.ielts.data.Utils.YouTubeLink


data class DashboardItems(
    val itemImageUri: String? = null,
    val itemText: String? = null,
    val cardType: String? = null,
    val color: Int = Color.GRAY,
    val query: String = YouTubeLink.link
)
