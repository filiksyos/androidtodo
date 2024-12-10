package com.wolfbytetechnologies.ielts.repo

import android.graphics.drawable.Drawable

interface ResourceProvider {
    fun getString(resId: Int): String
    fun getDrawable(resId: Int): Drawable?
    fun getColor(resId: Int): Int
    fun getQuery(query: String): String
}
