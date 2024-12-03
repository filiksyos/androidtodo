package com.wolfbytetechnologies.ielts.ui.dashboard.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import org.koin.android.ext.android.inject
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
        readingAdapter = DashboardAdapter(emptyList()) { position ->
            handleItemClick(dashboardViewModel.readingItems.value?.get(position))
        }

        listeningAdapter = DashboardAdapter(emptyList()) { position ->
            handleItemClick(dashboardViewModel.listeningItems.value?.get(position))
        }

        writingAdapter = DashboardAdapter(emptyList()) { position ->
            handleItemClick(dashboardViewModel.writingItems.value?.get(position))
        }

        speakingAdapter = DashboardAdapter(emptyList()) { position ->
            handleItemClick(dashboardViewModel.speakingItems.value?.get(position))
        }
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
            readingAdapter = DashboardAdapter(items) { position ->
                handleItemClick(items[position])
            }
            binding.rvReading.adapter = readingAdapter
        }

        dashboardViewModel.listeningItems.observe(viewLifecycleOwner) { items ->
            listeningAdapter = DashboardAdapter(items) { position ->
                handleItemClick(items[position])
            }
            binding.rvListening.adapter = listeningAdapter
        }

        dashboardViewModel.writingItems.observe(viewLifecycleOwner) { items ->
            writingAdapter = DashboardAdapter(items) { position ->
                handleItemClick(items[position])
            }
            binding.rvWriting.adapter = writingAdapter
        }

        dashboardViewModel.speakingItems.observe(viewLifecycleOwner) { items ->
            speakingAdapter = DashboardAdapter(items) { position ->
                handleItemClick(items[position])
            }
            binding.rvSpeaking.adapter = speakingAdapter
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
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "No app available to open the link", Toast.LENGTH_SHORT).show()
        }
    }
}
