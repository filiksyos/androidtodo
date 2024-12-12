package com.wolfbytetechnologies.ielts.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfbytetechnologies.ielts.data.DashboardItems

class DashboardViewModel(
    private val getDashboardItemsUseCase: GetDashboardItemsUseCase
) : ViewModel() {

    private val _readingItems = MutableLiveData<List<DashboardItems>>()
    val readingItems: LiveData<List<DashboardItems>> get() = _readingItems

    private val _listeningItems = MutableLiveData<List<DashboardItems>>()
    val listeningItems: LiveData<List<DashboardItems>> get() = _listeningItems

    private val _writingItems = MutableLiveData<List<DashboardItems>>()
    val writingItems: LiveData<List<DashboardItems>> get() = _writingItems

    private val _speakingItems = MutableLiveData<List<DashboardItems>>()
    val speakingItems: LiveData<List<DashboardItems>> get() = _speakingItems

    fun loadDashboardItems() {
        _readingItems.value = getDashboardItemsUseCase.getReadingItems()
        _listeningItems.value = getDashboardItemsUseCase.getListeningItems()
        _writingItems.value = getDashboardItemsUseCase.getWritingItems()
        _speakingItems.value = getDashboardItemsUseCase.getSpeakingItems()
    }

}

