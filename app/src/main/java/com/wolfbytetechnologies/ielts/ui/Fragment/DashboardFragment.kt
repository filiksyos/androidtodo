// DashboardFragment.kt
package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.data.Utils.Logger
import com.wolfbytetechnologies.ielts.ui.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.viewModel.DashboardViewModel
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

        lifecycleScope.launch {
            dashboardViewModel.readingPagingFlow.collectLatest { pagingData ->
                readingAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.listeningPagingFlow.collectLatest { pagingData ->
                listeningAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.writingPagingFlow.collectLatest { pagingData ->
                writingAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.speakingPagingFlow.collectLatest { pagingData ->
                speakingAdapter.submitData(pagingData)
            }
        }

    }

    private fun navigateToYouTube(query: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
        startActivity(intent)
    }
}
