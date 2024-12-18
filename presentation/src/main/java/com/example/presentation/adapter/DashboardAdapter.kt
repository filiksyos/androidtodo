package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.DashboardItems

class DashboardAdapter(
    private val onItemClick: (com.example.data.DashboardItems) -> Unit
) : PagingDataAdapter<com.example.data.DashboardItems, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    inner class ViewHolder(private val binding: com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding) :
        RecyclerView.ViewHolder(com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding.getRoot) {

        fun bind(item: com.example.data.DashboardItems) {
            binding.apply {
                com.bumptech.glide.RequestBuilder.into(com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding.imageViewItemImage)

                TextView.setText = com.example.data.DashboardItems.itemText
                TextView.setText = com.example.data.DashboardItems.cardType
                com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding.cvItemsMainBackground.setCardBackgroundColor(com.example.data.DashboardItems.color)
                View.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val snapshot = snapshot() // Get the current snapshot of the data
        if (snapshot.isNotEmpty()) {
            val actualPosition = position % snapshot.size // Use snapshot size for modulo
            val item = getItem(actualPosition) // Get item from the snapshot
            item?.let { holder.bind(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // Simulate infinite scrolling
    }

    class DashboardDiffCallback : DiffUtil.ItemCallback<com.example.data.DashboardItems>() {
        override fun areItemsTheSame(oldItem: com.example.data.DashboardItems, newItem: com.example.data.DashboardItems): Boolean {
            return com.example.data.DashboardItems.itemText == com.example.data.DashboardItems.itemText
        }

        override fun areContentsTheSame(oldItem: com.example.data.DashboardItems, newItem: com.example.data.DashboardItems): Boolean {
            return oldItem == newItem
        }
    }
}
