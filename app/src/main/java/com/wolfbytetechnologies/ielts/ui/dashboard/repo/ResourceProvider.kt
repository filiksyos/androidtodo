package com.wolfbytetechnologies.ielts.ui.dashboard.repo

import android.graphics.drawable.Drawable

interface ResourceProvider {
    fun getString(resId: Int): String
    fun getDrawable(resId: Int): Drawable?
    fun getColor(resId: Int): Int
}
