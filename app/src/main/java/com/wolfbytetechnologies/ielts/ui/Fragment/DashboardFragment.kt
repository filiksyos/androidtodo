package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.data.DashboardItems
import com.wolfbytetechnologies.ielts.viewModel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()

    // Separate adapters for each category
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

        // Observe LiveData for updates
        observeViewModel()

        // Load dashboard items
        dashboardViewModel.loadDashboardItems()
    }

    private fun setupAdapters() {
        readingAdapter = DashboardAdapter { item ->
            navigateToYouTube(item)
        }
        listeningAdapter = DashboardAdapter { item ->
            navigateToYouTube(item)
        }
        writingAdapter = DashboardAdapter { item ->
            navigateToYouTube(item)
        }
        speakingAdapter = DashboardAdapter { item ->
            navigateToYouTube(item)
        }
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

        // Attach distinct adapters to each RecyclerView
        binding.rvReading.adapter = readingAdapter
        binding.rvListening.adapter = listeningAdapter
        binding.rvWriting.adapter = writingAdapter
        binding.rvSpeaking.adapter = speakingAdapter
    }

    private fun observeViewModel() {
        dashboardViewModel.readingItems.observe(viewLifecycleOwner) { items ->
            readingAdapter.submitList(items)
        }

        dashboardViewModel.listeningItems.observe(viewLifecycleOwner) { items ->
            listeningAdapter.submitList(items)
        }

        dashboardViewModel.writingItems.observe(viewLifecycleOwner) { items ->
            writingAdapter.submitList(items)
        }

        dashboardViewModel.speakingItems.observe(viewLifecycleOwner) { items ->
            speakingAdapter.submitList(items)
        }
    }

    private fun navigateToYouTube(item: DashboardItems) {
        val query = "${item.itemText} ${item.cardType} IELTS".trim()
        val link = "https://www.youtube.com/results?search_query=${Uri.encode(query)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        if (requireContext().packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "No app available to open the link.", Toast.LENGTH_SHORT).show()
        }
    }
}
