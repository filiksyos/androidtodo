package com.example.presentation.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.DashboardItems
import com.example.data.repo.Repository

class DashboardPagingSource(
    private val repository: Repository,
    private val category: String
) : PagingSource<Int, DashboardItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardItems> {
        val pageNumber = params.key ?: 0
        return try {
            val items = when (category) {
                "READING" -> Repository.getReadingItems().getOrElse { emptyList<DashboardItems>() }
                "LISTENING" -> Repository.getListeningItems().getOrElse { emptyList<DashboardItems>() }
                "WRITING" -> Repository.getWritingItems().getOrElse { emptyList<DashboardItems>() }
                "SPEAKING" -> Repository.getSpeakingItems().getOrElse { emptyList<DashboardItems>() }
                else -> emptyList<DashboardItems>()
            }

            LoadResult.Page(
                data = items,
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
