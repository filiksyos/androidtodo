package com.wolfbytetechnologies.ielts.Networking

data class Questions(
    val id: Int,
    val type: String,
    val topic: String,
    val questionType: String,
    val title: String,
    val content: String
)
