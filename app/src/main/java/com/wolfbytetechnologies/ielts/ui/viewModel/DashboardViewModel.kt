package com.wolfbytetechnologies.ielts.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wolfbytetechnologies.ielts.ui.adapter.DashboardPagingSource
import com.wolfbytetechnologies.ielts.data.repo.Repository

class DashboardViewModel(
    private val repository: Repository
) : ViewModel() {

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
}
