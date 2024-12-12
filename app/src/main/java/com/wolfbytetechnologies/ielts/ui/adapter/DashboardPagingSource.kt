package com.wolfbytetechnologies.ielts.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wolfbytetechnologies.ielts.data.DashboardItems

class DashboardPagingSource(
    private val getItems: suspend (Int, Int) -> List<DashboardItems>
) : PagingSource<Int, DashboardItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardItems> {
        return try {
            val currentPage = params.key ?: 0
            val pageSize = params.loadSize

            // Fetch items from your data source
            val items = getItems(currentPage, pageSize)

            LoadResult.Page(
                data = items,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (items.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DashboardItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
