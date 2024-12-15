package com.wolfbytetechnologies.ielts.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding
import com.bumptech.glide.Glide
import com.wolfbytetechnologies.ielts.R


class DashboardAdapter(
    private val onItemClick: (DashboardItems) -> Unit
) : ListAdapter<DashboardItems, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    inner class ViewHolder(private val binding: DashboardCardviewItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardItems) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(Uri.parse(item.itemImageUri))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(imageViewItemImage)

                tvItemName.text = item.itemText
                tvLessonOrTest.text = item.cardType
                cvItemsMainBackground.setCardBackgroundColor(item.color)
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DashboardCardviewItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) } // Handle null safety as PagingDataAdapter can return null for placeholders
    }
}

