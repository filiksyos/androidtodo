package com.example.presentation.Fragment

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteLinearLayoutManager(
    context: Context,
    orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        // Set the initial position to the middle before the RecyclerView renders
        view?.scrollToPosition(Int.MAX_VALUE / 2)
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false // Disable predictive animations to avoid flicker
    }
}

