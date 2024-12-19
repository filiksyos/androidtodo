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

class DashboardViewModel(private val getDashboardItemsUseCase: GetDashboardItemsUseCase) : ViewModel() {

    private val _dashboardItems = MutableLiveData<Map<DashboardCategory, List<DashboardItems>>>()
    val dashboardItems: LiveData<Map<DashboardCategory, List<DashboardItems>>> get() = _dashboardItems

    fun loadDashboardItems() {
        viewModelScope.launch {
            val itemsMap = DashboardCategory.values().associateWith { category ->
                getDashboardItemsUseCase(category) // Invoke the use case directly
            }
            _dashboardItems.value = itemsMap
        }
    }
}