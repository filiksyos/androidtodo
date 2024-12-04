package com.wolfbytetechnologies.ielts.ui.dashboard.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.Utils.InternetUtility
import com.wolfbytetechnologies.ielts.Utils.Logger
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private val internetUtility: InternetUtility by inject()

    private lateinit var readingAdapter: DashboardAdapter
    private lateinit var listeningAdapter: DashboardAdapter
    private lateinit var writingAdapter: DashboardAdapter
    private lateinit var speakingAdapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupRecyclerViews()
        setupObservers()

        // Trigger data categorization
        dashboardViewModel.categorizeDashboardItems()
    }

    private fun setupAdapters() {
        readingAdapter = DashboardAdapter { position ->
            handleItemClick(dashboardViewModel.readingItems.value?.get(position))
        }

        listeningAdapter = DashboardAdapter { position ->
            handleItemClick(dashboardViewModel.listeningItems.value?.get(position))
        }

        writingAdapter = DashboardAdapter { position ->
            handleItemClick(dashboardViewModel.writingItems.value?.get(position))
        }

        speakingAdapter = DashboardAdapter { position ->
            handleItemClick(dashboardViewModel.speakingItems.value?.get(position))
        }

        // Attach adapters to RecyclerViews
        binding.rvReading.adapter = readingAdapter
        binding.rvListening.adapter = listeningAdapter
        binding.rvWriting.adapter = writingAdapter
        binding.rvSpeaking.adapter = speakingAdapter
    }


    private fun setupRecyclerViews() {
        binding.rvReading.apply {
            adapter = readingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvListening.apply {
            adapter = listeningAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvWriting.apply {
            adapter = writingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvSpeaking.apply {
            adapter = speakingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupObservers() {
        dashboardViewModel.readingItems.observe(viewLifecycleOwner) { items ->
            Logger.logDebug("DashboardFragment", "Reading Items Updated: ${items?.size ?: 0}")
            readingAdapter.submitList(items ?: emptyList())
        }

        dashboardViewModel.listeningItems.observe(viewLifecycleOwner) { items ->
            Logger.logDebug("DashboardFragment", "Listening Items Updated: ${items?.size ?: 0}")
            listeningAdapter.submitList(items ?: emptyList())
        }

        dashboardViewModel.writingItems.observe(viewLifecycleOwner) { items ->
            Logger.logDebug("DashboardFragment", "Writing Items Updated: ${items?.size ?: 0}")
            writingAdapter.submitList(items ?: emptyList())
        }

        dashboardViewModel.speakingItems.observe(viewLifecycleOwner) { items ->
            Logger.logDebug("DashboardFragment", "Speaking Items Updated: ${items?.size ?: 0}")
            speakingAdapter.submitList(items ?: emptyList())
        }
    }

    private fun handleItemClick(item: DashboardItems?) {
        item?.let {
            val query = "${it.itemText} ${it.cardType} IELTS".trim() // e.g., "Reading Lesson IELTS"
            openYouTubeSearch(query)
        }
    }

    private fun openYouTubeSearch(query: String) {
        val link = "https://www.youtube.com/results?search_query=${Uri.encode(query)}"
        Logger.logDebug("DashboardFragment", "Navigating to YouTube with query: $query")

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))

        if (internetUtility.isConnected) {
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                Logger.logError("DashboardFragment", "No app available to handle the YouTube link.")
                Toast.makeText(requireContext(), "No app available to open the link", Toast.LENGTH_SHORT).show()
            }
        } else {
            Logger.logError("DashboardFragment", "No internet connection.")
            Toast.makeText(requireContext(), "No internet connection. Please check your connection.", Toast.LENGTH_SHORT).show()
        }
    }

}
