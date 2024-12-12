package com.wolfbytetechnologies.ielts.data.repo

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.wolfbytetechnologies.ielts.data.Utils.YouTubeLink

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)
    override fun getColor(resId: Int): Int = ContextCompat.getColor(context, resId)
    override fun getQuery(query: String): String = YouTubeLink.getLink(query)
}
