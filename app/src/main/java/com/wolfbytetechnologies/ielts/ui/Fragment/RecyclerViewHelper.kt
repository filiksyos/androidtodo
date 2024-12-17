package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewHelper {

    fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        context: Context,
        isHorizontal: Boolean = true
    ) {
        recyclerView.apply {
            layoutManager = InfiniteLinearLayoutManager(
                context,
                if (isHorizontal) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL,
                false
            )
            this.adapter = adapter
        }
    }
}
