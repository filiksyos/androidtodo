package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.ui.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.viewModel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.wolfbytetechnologies.ielts.Domain.GetDashboardItemsUseCase
import androidx.paging.cachedIn
import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.adapter.DashboardPagingSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()

    private lateinit var readingAdapter: DashboardAdapter
    private lateinit var listeningAdapter: DashboardAdapter
    private lateinit var writingAdapter: DashboardAdapter
    private lateinit var speakingAdapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupRecyclerViews()
        loadPagedItems()
    }

    private fun setupAdapters() {
        readingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        listeningAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        writingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        speakingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
    }

    private fun setupRecyclerViews() {
        binding.rvReading.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvListening.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvWriting.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSpeaking.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvReading.adapter = readingAdapter
        binding.rvListening.adapter = listeningAdapter
        binding.rvWriting.adapter = writingAdapter
        binding.rvSpeaking.adapter = speakingAdapter
    }

    private fun loadPagedItems() {
        lifecycleScope.launch {
            dashboardViewModel.readingPagingData.collectLatest { pagingData ->
                readingAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.listeningPagingData.collectLatest { pagingData ->
                listeningAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.writingPagingData.collectLatest { pagingData ->
                writingAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.speakingPagingData.collectLatest { pagingData ->
                speakingAdapter.submitData(pagingData)
            }
        }
    }

    private fun navigateToYouTube(query: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
        startActivity(intent)
    }
}

// DashboardPagingSource for Pagination
class DashboardPagingSource(
    private val getItems: suspend (Int, Int) -> List<DashboardItems>
) : PagingSource<Int, DashboardItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardItems> {
        return try {
            val currentPage = params.key ?: 0
            val pageSize = params.loadSize

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
