package com.wolfbytetechnologies.ielts.repo

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)
    override fun getColor(resId: Int): Int = ContextCompat.getColor(context, resId)
}
