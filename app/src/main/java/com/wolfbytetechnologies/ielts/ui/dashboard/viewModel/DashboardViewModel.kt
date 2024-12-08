package com.wolfbytetechnologies.ielts.ui.dashboard.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo

class DashboardViewModel(
    private val repo: MainDashboardItemsRepo,
    private val categorizeUseCase: CategorizeDashboardItems
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

    This area is commented because the app doesn't implement API calls yet.

    val apiResponse = MutableLiveData<ApiResponse<List<Questions>>>()

    fun fetchQuestions() {
        viewModelScope.launch {
            val response = NetworkRepository.getQuestions()
            apiResponse.postValue(response)
        }
    }

 */

}
