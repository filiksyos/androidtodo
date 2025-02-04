package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(
    val id: String,
    val owner: String,
    val title: String,
    val description: String = "",
    val dueDate: String = "",
    val completed: Boolean = false,
    val createdAt: String
) : Parcelable 