package com.example.data.repo

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.wolfbytetechnologies.ielts.data.Utils.YouTubeLink

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getDrawable(resId: Int): Drawable? =
        androidx.core.content.ContextCompat.getDrawable(context, resId)
    override fun getColor(resId: Int): Int =
        androidx.core.content.ContextCompat.getColor(context, resId)
    override fun getQuery(query: String): String =
        com.wolfbytetechnologies.ielts.data.Utils.YouTubeLink.getLink(query)
}
