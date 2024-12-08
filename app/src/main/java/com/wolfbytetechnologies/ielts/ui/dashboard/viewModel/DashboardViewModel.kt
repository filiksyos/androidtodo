package com.wolfbytetechnologies.ielts.ui.dashboard.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfbytetechnologies.ielts.Networking.ApiResponse
import com.wolfbytetechnologies.ielts.Networking.Questions
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import kotlinx.coroutines.launch
import com.wolfbytetechnologies.ielts.Networking.NetworkRepository

class DashboardViewModel(
    private val repo: MainDashboardItemsRepo,
    private val categorizeUseCase: CategorizeDashboardItemsUseCase
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
        val allItems = repo.getDashboardItems()
        val categorizedItems = categorizeUseCase.invoke(allItems)

        _readingItems.value = categorizedItems.readingItems
        _listeningItems.value = categorizedItems.listeningItems
        _writingItems.value = categorizedItems.writingItems
        _speakingItems.value = categorizedItems.speakingItems
    }

/*
    val apiResponse = MutableLiveData<ApiResponse<List<Questions>>>()

    fun fetchQuestions() {
        viewModelScope.launch {
            val response = NetworkRepository.getQuestions()
            apiResponse.postValue(response)
        }
    }

 */

}
