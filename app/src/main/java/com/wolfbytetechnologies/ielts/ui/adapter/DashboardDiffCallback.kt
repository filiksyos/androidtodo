package com.wolfbytetechnologies.ielts.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wolfbytetechnologies.ielts.data.DashboardItems

class DashboardDiffCallback : DiffUtil.ItemCallback<DashboardItems>() {
    override fun areItemsTheSame(oldItem: DashboardItems, newItem: DashboardItems): Boolean {
        return oldItem.itemText == newItem.itemText && oldItem.cardType == newItem.cardType
    }

    override fun areContentsTheSame(oldItem: DashboardItems, newItem: DashboardItems): Boolean {
        return oldItem == newItem
    }
}
