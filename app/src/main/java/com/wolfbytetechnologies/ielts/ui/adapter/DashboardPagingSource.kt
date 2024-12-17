package com.wolfbytetechnologies.ielts.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.data.repo.Repository

class DashboardPagingSource(
    private val repository: Repository,
    private val category: String
) : PagingSource<Int, DashboardItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardItems> {
        val pageNumber = params.key ?: 0
        return try {
            val items = when (category) {
                "READING" -> repository.getReadingItems().getOrElse { emptyList<DashboardItems>() }
                "LISTENING" -> repository.getListeningItems().getOrElse { emptyList<DashboardItems>() }
                "WRITING" -> repository.getWritingItems().getOrElse { emptyList<DashboardItems>() }
                "SPEAKING" -> repository.getSpeakingItems().getOrElse { emptyList<DashboardItems>() }
                else -> emptyList<DashboardItems>()
            }

            LoadResult.Page(
                data = items, // No need for getOrNull() here
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, DashboardItems>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
