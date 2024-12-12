package com.wolfbytetechnologies.ielts.adapter

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
                Glide.with(binding.root.context) // Use the context from the view binding
                    .load(Uri.parse(item.itemImageUri)) // Parse URI properly
                    .placeholder(R.drawable.placeholder) // Placeholder image while loading
                    .error(R.drawable.error_placeholder) // Fallback image in case of error
                    .into(imageViewItemImage) // Target ImageView

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
        holder.bind(currentItem)
    }
}
