package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.DashboardItems
import com.wolfbytetechnologies.presentation.databinding.DashboardCardviewItemsBinding
import com.bumptech.glide.Glide

class DashboardAdapter(
    private val onItemClick: (DashboardItems) -> Unit
) : PagingDataAdapter<DashboardItems, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    inner class ViewHolder(private val binding: DashboardCardviewItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardItems) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(imageViewItemImage)

                textViewItemText.text = item.itemText
                textViewCardType.text = item.cardType
                cvItemsMainBackground.setCardBackgroundColor(item.color)
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val snapshot = snapshot()
        if (snapshot.isNotEmpty()) {
            val actualPosition = position % snapshot.size
            val item = getItem(actualPosition)
            item?.let { holder.bind(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DashboardCardviewItemsBinding.inflate( // Use the imported class
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class DashboardDiffCallback : DiffUtil.ItemCallback<DashboardItems>() {
        override fun areItemsTheSame(oldItem: DashboardItems, newItem: DashboardItems): Boolean {
            return oldItem.itemText == newItem.itemText // Access item properties
        }

        override fun areContentsTheSame(oldItem: DashboardItems, newItem: DashboardItems): Boolean {
            return oldItem == newItem
        }
    }
}
