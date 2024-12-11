package com.wolfbytetechnologies.ielts.data

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.wolfbytetechnologies.ielts.Utils.YouTubeLink
import com.wolfbytetechnologies.ielts.R


data class DashboardItems(
    val itemImage: Drawable? = null,
    val itemText: String? = null,
    val cardType: String? = null,
    val color: Int = Color.GRAY,
    val query: String = YouTubeLink.link
)

