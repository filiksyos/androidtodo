package com.wolfbytetechnologies.ielts.ui.dashboard.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo

class DashboardViewModel(private val repo: MainDashboardItemsRepo) : ViewModel() {

    private val _readingItems = MutableLiveData<List<DashboardItems>>()
    val readingItems: LiveData<List<DashboardItems>> get() = _readingItems

    private val _listeningItems = MutableLiveData<List<DashboardItems>>()
    val listeningItems: LiveData<List<DashboardItems>> get() = _listeningItems

    private val _writingItems = MutableLiveData<List<DashboardItems>>()
    val writingItems: LiveData<List<DashboardItems>> get() = _writingItems

    private val _speakingItems = MutableLiveData<List<DashboardItems>>()
    val speakingItems: LiveData<List<DashboardItems>> get() = _speakingItems

    @RequiresApi(Build.VERSION_CODES.M)
    fun categorizeDashboardItems() {
        val allItems = repo.getDashboardItems()

        _readingItems.value = allItems.filter { it.itemText == "Reading" }
        _listeningItems.value = allItems.filter { it.itemText == "Listening" }
        _writingItems.value = allItems.filter { it.itemText == "Writing" }
        _speakingItems.value = allItems.filter { it.itemText == "Speaking" }
    }
}
