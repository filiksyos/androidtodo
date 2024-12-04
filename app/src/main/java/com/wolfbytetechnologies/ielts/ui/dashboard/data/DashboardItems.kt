package com.wolfbytetechnologies.ielts.ui.dashboard.data

import android.graphics.Color
import android.graphics.drawable.Drawable

data class DashboardItems(
    val itemImage: Drawable? = null,
    val itemText: String = "Unknown",
    val cardType: String = "Undefined",
    val color: Int = Color.GRAY
)

