package com.example.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.DashboardItems
import com.bumptech.glide.Glide
import com.example.presentation.databinding.DashboardCardviewItemsBinding

class DashboardAdapter(
    private val onItemClick: (DashboardItems) -> Unit
) : PagingDataAdapter<DashboardItems, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    inner class ViewHolder(private val binding: DashboardCardviewItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardItems) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(Uri.parse(item.itemImageUri))
                    .into(imageViewItemImage)

                tvItemName.text = item.itemText
                tvLessonOrTest.text = item.cardType
                cvItemsMainBackground.setCardBackgroundColor(item.color)
                root.setOnClickListener { onItemClick(item) }
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
        val binding = DashboardCardviewItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // Simulate infinite scrolling
    }

    class DashboardDiffCallback : DiffUtil.ItemCallback<DashboardItems>() {
        override fun areItemsTheSame(oldItem: DashboardItems, newItem: DashboardItems): Boolean {
            return oldItem.itemText == newItem.itemText
        }

        override fun areContentsTheSame(oldItem: DashboardItems, newItem: DashboardItems): Boolean {
            return oldItem == newItem
        }
    }
}