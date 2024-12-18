package com.example.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.DashboardItems
import com.example.data.Repository
import com.example.presentation.adapter.DashboardPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DashboardViewModel(private val repository: Repository) : ViewModel() {

    // State flows for each category
    private val _readingState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val readingState: StateFlow<DashboardState> = _readingState.asStateFlow()

    private val _listeningState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val listeningState: StateFlow<DashboardState> = _listeningState.asStateFlow()

    private val _writingState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val writingState: StateFlow<DashboardState> = _writingState.asStateFlow()

    private val _speakingState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val speakingState: StateFlow<DashboardState> = _speakingState.asStateFlow()

    // Flows for PagingData
    val readingPagingFlow = Pager(PagingConfig(pageSize = 10)) {
        DashboardPagingSource(repository, "READING")
    }.flow.cachedIn(viewModelScope)

    val listeningPagingFlow = Pager(PagingConfig(pageSize = 10)) {
        DashboardPagingSource(repository, "LISTENING")
    }.flow.cachedIn(viewModelScope)

    val writingPagingFlow = Pager(PagingConfig(pageSize = 10)) {
        DashboardPagingSource(repository, "WRITING")
    }.flow.cachedIn(viewModelScope)

    val speakingPagingFlow = Pager(PagingConfig(pageSize = 10)) {
        DashboardPagingSource(repository, "SPEAKING")
    }.flow.cachedIn(viewModelScope)

    // Initialize states for observing PagingData load states
    init {
        observePagingState(readingPagingFlow, _readingState)
        observePagingState(listeningPagingFlow, _listeningState)
        observePagingState(writingPagingFlow, _writingState)
        observePagingState(speakingPagingFlow, _speakingState)
    }

    private fun observePagingState(
        pagingFlow: Flow<PagingData<DashboardItems>>,
        stateFlow: MutableStateFlow<DashboardState>
    ) {
        viewModelScope.launch {
            pagingFlow.collectLatest { pagingData ->
                stateFlow.value = DashboardState.Success(pagingData)
            }
        }
    }
}
