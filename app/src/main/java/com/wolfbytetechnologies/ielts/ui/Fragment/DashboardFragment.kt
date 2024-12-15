// DashboardFragment.kt
package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.data.Utils.Logger
import com.wolfbytetechnologies.ielts.ui.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.viewModel.DashboardViewModel
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
        dashboardViewModel.loadDashboardItems()
    }

    private fun setupAdapters() {
        readingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        listeningAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        writingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
        speakingAdapter = DashboardAdapter { item -> navigateToYouTube(item.query) }
    }

    private fun setupRecyclerViews() {
        binding.rvReading.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvListening.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvWriting.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSpeaking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



        binding.rvReading.adapter = readingAdapter
        binding.rvListening.adapter = listeningAdapter
        binding.rvWriting.adapter = writingAdapter
        binding.rvSpeaking.adapter = speakingAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            dashboardViewModel.readingItems.collect { items ->
                if (items.isNotEmpty()) {
                    readingAdapter.submitList(items)
                    Logger.logDebug("DashboardFragment", "Reading items: ${items}")
                }

            }
        }

        lifecycleScope.launch {
            dashboardViewModel.listeningItems.collect { items ->
                if (items.isNotEmpty()) {
                    listeningAdapter.submitList(items)
                    Logger.logDebug("DashboardFragment", "Listening items: ${items}")
                }
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.writingItems.collect { items ->
                if (items.isNotEmpty()) {
                    writingAdapter.submitList(items)
                }
            }
        }

        lifecycleScope.launch {
            dashboardViewModel.speakingItems.collect { items ->
                if (items.isNotEmpty()) {
                    speakingAdapter.submitList(items)
                }
            }
        }
    }

    private fun navigateToYouTube(query: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
        startActivity(intent)
    }
}
