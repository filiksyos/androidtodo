package com.example.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.data.Utils.YouTubeLink
import com.example.domain.ResourceProvider

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)
    override fun getColor(resId: Int): Int = ContextCompat.getColor(context, resId)
    override fun getQuery(query: String): String =YouTubeLink.getLink(query)
}
