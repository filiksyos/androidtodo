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
import com.wolfbytetechnologies.ielts.data.Utils.Result

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

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    fun loadDashboardItems() {
        viewModelScope.launch {
            // Fetch reading items
            when (val result = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.READING)) {
                else -> _readingItems.value = result.getOrNull() ?: emptyList()
            }

            when (val result = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.LISTENING)) {
                else -> _listeningItems.value = result.getOrNull() ?: emptyList()
            }


            when (val result = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.WRITING)) {
                else -> _writingItems.value = result.getOrNull() ?: emptyList()
            }

            when (val result = getDashboardItemsUseCase.getDashboardItems(DashboardCategory.SPEAKING)) {
                else -> _speakingItems.value = result.getOrNull() ?: emptyList()
            }

        }
    }
}
