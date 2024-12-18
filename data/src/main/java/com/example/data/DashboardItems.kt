package com.example.data

import android.graphics.Color


data class DashboardItems(
    val itemImageUri: String? = null,
    val itemText: String? = null,
    val cardType: String? = null,
    val color: Int = Color.GRAY,
    val query: String = com.example.data.Utils.YouTubeLink.link
)
