package com.example.presentation.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState

class DashboardPagingSource(
    private val repository: com.example.data.repo.Repository,
    private val category: String
) : PagingSource<Int, com.example.data.DashboardItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.data.DashboardItems> {
        val pageNumber = params.key ?: 0
        return try {
            val items = when (category) {
                "READING" -> com.example.data.repo.Repository.getReadingItems().getOrElse { emptyList<com.example.data.DashboardItems>() }
                "LISTENING" -> com.example.data.repo.Repository.getListeningItems().getOrElse { emptyList<com.example.data.DashboardItems>() }
                "WRITING" -> com.example.data.repo.Repository.getWritingItems().getOrElse { emptyList<com.example.data.DashboardItems>() }
                "SPEAKING" -> com.example.data.repo.Repository.getSpeakingItems().getOrElse { emptyList<com.example.data.DashboardItems>() }
                else -> emptyList<com.example.data.DashboardItems>()
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
    override fun getRefreshKey(state: PagingState<Int, com.example.data.DashboardItems>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
