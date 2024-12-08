package com.abdulhaseeb.ielts_practice_app.RepositoryTest

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.ResourceProvider
import com.wolfbytetechnologies.ielts.R

class FakeResourceProvider : ResourceProvider {
    override fun getString(resId: Int): String {
        return when (resId) {
            R.string.reading -> "Fake Reading"
            R.string.listening -> "Fake Listening"
            R.string.writing -> "Fake Writing"
            R.string.speaking -> "Fake Speaking"
            else -> "Fake Default"
        }
    }

    override fun getDrawable(resId: Int): Drawable? {
        return null // Return null or mock a drawable
    }

    override fun getColor(resId: Int): Int {
        return Color.BLACK // Return a default color
    }
}
