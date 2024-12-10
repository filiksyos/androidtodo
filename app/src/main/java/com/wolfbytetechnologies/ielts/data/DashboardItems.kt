package com.wolfbytetechnologies.ielts.data

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.wolfbytetechnologies.ielts.Utils.YouTubeLink

data class DashboardItems(
    val itemImage: Drawable? = null,
    val itemText: String = "Unknown",
    val cardType: String = "Undefined",
    val color: Int = Color.GRAY,
    val query: String = YouTubeLink.link
)

