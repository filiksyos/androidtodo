package com.example.presentation.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.data.DashboardItems
import com.example.presentation.adapter.DashboardAdapter
import com.example.presentation.databinding.FragmentDashboardBinding
import com.example.presentation.viewModel.DashboardState
import com.example.presentation.viewModel.DashboardViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        observeViewModel()
        //dashboardViewModel.loadDashboardItems()
    }

    private fun setupAdapters() {
        readingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        listeningAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        writingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        speakingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
    }

    private fun setupRecyclerViews() {
        RecyclerViewHelper.setupRecyclerView(binding.rvReading, readingAdapter, requireContext())
        binding.rvReading.scrollToPosition(Int.MAX_VALUE / 2) // Start in the middle for seamless looping
        RecyclerViewHelper.setupRecyclerView(binding.rvListening, listeningAdapter, requireContext())
        binding.rvReading.scrollToPosition(Int.MAX_VALUE / 2)
        RecyclerViewHelper.setupRecyclerView(binding.rvWriting, writingAdapter, requireContext())
        binding.rvReading.scrollToPosition(Int.MAX_VALUE / 2)
        RecyclerViewHelper.setupRecyclerView(binding.rvSpeaking, speakingAdapter, requireContext())
        binding.rvReading.scrollToPosition(Int.MAX_VALUE / 2)
    }

    private fun observeViewModel() {
        observeCategory(
            pagingFlow = dashboardViewModel.readingPagingFlow,
            stateFlow = dashboardViewModel.readingState,
            adapter = readingAdapter,
            recyclerView = binding.rvReading
        )
        observeCategory(
            pagingFlow = dashboardViewModel.listeningPagingFlow,
            stateFlow = dashboardViewModel.listeningState,
            adapter = listeningAdapter,
            recyclerView = binding.rvListening
        )
        observeCategory(
            pagingFlow = dashboardViewModel.writingPagingFlow,
            stateFlow = dashboardViewModel.writingState,
            adapter = writingAdapter,
            recyclerView = binding.rvWriting
        )
        observeCategory(
            pagingFlow = dashboardViewModel.speakingPagingFlow,
            stateFlow = dashboardViewModel.speakingState,
            adapter = speakingAdapter,
            recyclerView = binding.rvSpeaking
        )
    }

    private fun observeCategory(
        pagingFlow: Flow<PagingData<DashboardItems>>,
        stateFlow: StateFlow<DashboardState>,
        adapter: DashboardAdapter,
        recyclerView: RecyclerView
    ) {
        lifecycleScope.launch {
            pagingFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            stateFlow.collect { state ->
                when (state) {
                    is DashboardState.Loading -> showLoading(recyclerView)
                    is DashboardState.Success -> showRecyclerView(recyclerView)
                    is DashboardState.Empty -> showEmptyState(recyclerView)
                    is DashboardState.Error -> showError(recyclerView, state.message)
                }
            }
        }
    }

    private fun showLoading(recyclerView: RecyclerView) {
        recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showRecyclerView(recyclerView: RecyclerView) {
        recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showEmptyState(recyclerView: RecyclerView) {
        recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        Toast.makeText(requireContext(), "No items found", Toast.LENGTH_SHORT).show()
    }

    private fun showError(recyclerView: RecyclerView, message: String) {
        recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_LONG).show()
    }


    private fun navigateToYouTube(query: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
        startActivity(intent)
    }
}