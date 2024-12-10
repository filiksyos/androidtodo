package com.wolfbytetechnologies.ielts.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.repo.Repository

class DashboardViewModel(
    private val repo: Repository
) : ViewModel() {

    private val _readingItems = MutableLiveData<List<DashboardItems>>()
    val readingItems: LiveData<List<DashboardItems>> get() = _readingItems

    private val _listeningItems = MutableLiveData<List<DashboardItems>>()
    val listeningItems: LiveData<List<DashboardItems>> get() = _listeningItems

    private val _writingItems = MutableLiveData<List<DashboardItems>>()
    val writingItems: LiveData<List<DashboardItems>> get() = _writingItems

    private val _speakingItems = MutableLiveData<List<DashboardItems>>()
    val speakingItems: LiveData<List<DashboardItems>> get() = _speakingItems

    /**
     * Loads items from the repository and updates LiveData for each category.
     */
    fun loadDashboardItems() {
        _readingItems.value = repo.getReadingItems()
        _listeningItems.value = repo.getListeningItems()
        _writingItems.value = repo.getWritingItems()
        _speakingItems.value = repo.getSpeakingItems()
    }
}

