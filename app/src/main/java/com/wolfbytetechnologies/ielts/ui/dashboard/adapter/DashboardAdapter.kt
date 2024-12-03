package com.wolfbytetechnologies.ielts.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.databinding.DashboardCardviewItemsBinding

class DashboardAdapter(
    val itemOnClick: (Int) -> Unit
) : ListAdapter<DashboardItems, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    class ViewHolder(
        val binding: DashboardCardviewItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DashboardCardviewItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            imageViewItemImage.setImageDrawable(currentItem.itemImage)
            tvItemName.text = currentItem.itemText
            tvLessonOrTest.text = currentItem.cardType
            if (currentItem.color != null)
                cvItemsMainBackground.setCardBackgroundColor(currentItem.color)
        }
        holder.itemView.setOnClickListener {
            itemOnClick(holder.adapterPosition)
        }
    }
}
