package com.wolfbytetechnologies.ielts.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wolfbytetechnologies.ielts.Domain.GetDashboardItemsUseCase
import androidx.paging.cachedIn
import com.wolfbytetechnologies.ielts.ui.adapter.DashboardPagingSource

class DashboardViewModel(
    private val getDashboardItemsUseCase: GetDashboardItemsUseCase
) : ViewModel() {

    val readingPagingData = Pager(
        config = PagingConfig(
            pageSize = 20, // Number of items to load per page
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            DashboardPagingSource { page, size ->
                getDashboardItemsUseCase.getReadingItems(page, size)
            }
        }
    ).flow.cachedIn(viewModelScope)

    val listeningPagingData = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            DashboardPagingSource { page, size ->
                getDashboardItemsUseCase.getListeningItems(page, size)
            }
        }
    ).flow.cachedIn(viewModelScope)

    val writingPagingData = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            DashboardPagingSource { page, size ->
                getDashboardItemsUseCase.getWritingItems(page, size)
            }
        }
    ).flow.cachedIn(viewModelScope)

    val speakingPagingData = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            DashboardPagingSource { page, size ->
                getDashboardItemsUseCase.getSpeakingItems(page, size)
            }
        }
    ).flow.cachedIn(viewModelScope)
}


