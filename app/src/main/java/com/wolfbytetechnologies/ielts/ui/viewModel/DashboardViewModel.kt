// DashboardViewModel.kt
package com.wolfbytetechnologies.ielts.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfbytetechnologies.ielts.Domain.GetDashboardItemsUseCase
import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.Domain.DashboardCategory
import com.wolfbytetechnologies.ielts.data.Utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardItemsUseCase: GetDashboardItemsUseCase
) : ViewModel() {

    private val _readingItems = MutableStateFlow<List<DashboardItems>>(emptyList())
    val readingItems: StateFlow<List<DashboardItems>> get() = _readingItems.asStateFlow()

    private val _listeningItems = MutableStateFlow<List<DashboardItems>>(emptyList())
    val listeningItems: StateFlow<List<DashboardItems>> get() = _listeningItems.asStateFlow()

    private val _writingItems = MutableStateFlow<List<DashboardItems>>(emptyList())
    val writingItems: StateFlow<List<DashboardItems>> get() = _writingItems.asStateFlow()

    private val _speakingItems = MutableStateFlow<List<DashboardItems>>(emptyList())
    val speakingItems: StateFlow<List<DashboardItems>> get() = _speakingItems.asStateFlow()

    fun loadDashboardItems() {
        viewModelScope.launch {
            _readingItems.value = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.READING)
            Logger.logDebug("DashboardViewModel", "Reading items: ${_readingItems.value}")
            _listeningItems.value = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.LISTENING)
            _writingItems.value = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.WRITING)
            _speakingItems.value = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.SPEAKING)
        }
    }
}
