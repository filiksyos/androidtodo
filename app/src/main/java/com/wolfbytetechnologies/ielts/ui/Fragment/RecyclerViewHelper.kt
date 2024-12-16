package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewHelper {

    /**
     * Sets up a RecyclerView with an InfiniteLinearLayoutManager and an adapter.
     *
     * @param recyclerView The RecyclerView to set up.
     * @param adapter The RecyclerView.Adapter instance.
     * @param context The context for the InfiniteLinearLayoutManager.
     * @param isHorizontal Whether the layout should scroll horizontally (default: true).
     */
    fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        context: Context,
        isHorizontal: Boolean = true
    ) {
        recyclerView.apply {
            layoutManager = InfiniteLinearLayoutManager( // Use InfiniteLinearLayoutManager
                context,
                if (isHorizontal) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL,
                false
            )
            this.adapter = adapter
        }
    }
}