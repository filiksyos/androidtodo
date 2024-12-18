package com.example.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.data.DashboardItems

class DashboardDiffCallback : DiffUtil.ItemCallback<com.example.data.DashboardItems>() {
    override fun areItemsTheSame(oldItem: com.example.data.DashboardItems, newItem: com.example.data.DashboardItems): Boolean {
        return com.example.data.DashboardItems.itemText == com.example.data.DashboardItems.itemText && com.example.data.DashboardItems.cardType == com.example.data.DashboardItems.cardType
    }

    override fun areContentsTheSame(oldItem: com.example.data.DashboardItems, newItem: com.example.data.DashboardItems): Boolean {
        return oldItem == newItem
    }
}

