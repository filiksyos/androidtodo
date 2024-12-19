package com.example.presentation.viewModel

import androidx.core.graphics.values
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DashboardItems
import com.example.domain.DashboardCategory
import com.example.domain.GetDashboardItemsUseCase
import kotlinx.coroutines.launch
import kotlin.invoke

class DashboardViewModel(private val getDashboardItemsUseCase: GetDashboardItemsUseCase) : ViewModel() {

    private val _dashboardItems = MutableLiveData<Map<DashboardCategory, List<DashboardItems>>>()
    val dashboardItems: LiveData<Map<DashboardCategory, List<DashboardItems>>> get() = _dashboardItems

    fun loadDashboardItems() {
        viewModelScope.launch {
            val itemsMap = DashboardCategory.entries.associateWith { category ->
                getDashboardItemsUseCase.invoke(category) // Ensure invoke is called correctly
            }
            _dashboardItems.postValue(itemsMap) // Use postValue for background threads
        }
    }
}