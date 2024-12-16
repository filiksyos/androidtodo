package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class InfiniteLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false // Avoid predictive animations for large position values
    }
}
