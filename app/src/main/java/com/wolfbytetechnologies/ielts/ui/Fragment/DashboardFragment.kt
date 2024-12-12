package com.wolfbytetechnologies.ielts.ui.Fragment

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.viewModel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
        private val dashboardViewModel: DashboardViewModel by viewModel()

    private lateinit var readingAdapter: DashboardAdapter
    private lateinit var listeningAdapter: DashboardAdapter
    private lateinit var writingAdapter: DashboardAdapter
    private lateinit var speakingAdapter: DashboardAdapter

    private val loadedSections = mutableSetOf<String>()

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

        // Safely initialize data loading
        try {
            loadReadingItems()
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error loading reading items: ${e.message}")
        }

        // Lazy loading for other RecyclerViews as user scrolls
        setupLazyLoading()
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

    private fun setupLazyLoading() {
        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (isViewVisible(binding.rvReading) && "reading" !in loadedSections) {
                loadReadingItems()
            }
            if (isViewVisible(binding.rvListening) && "listening" !in loadedSections) {
                loadListeningItems()
            }
            if (isViewVisible(binding.rvWriting) && "writing" !in loadedSections) {
                loadWritingItems()
            }
            if (isViewVisible(binding.rvSpeaking) && "speaking" !in loadedSections) {
                loadSpeakingItems()
            }
        }
    }

    private fun isViewVisible(view: View): Boolean {
        val scrollBounds = Rect()
        binding.nestedScrollView.getHitRect(scrollBounds)
        return view.getLocalVisibleRect(scrollBounds)
    }

    private fun loadReadingItems() {
        if (!loadedSections.contains("reading")) {
            dashboardViewModel.readingItems.observe(viewLifecycleOwner) { items ->
                if (items != null) {
                    readingAdapter.submitList(items)
                    loadedSections.add("reading")
                } else {
                    Log.w("DashboardFragment", "No reading items found.")
                }
            }
        }
    }

    private fun loadListeningItems() {
        if (!loadedSections.contains("listening")) {
            dashboardViewModel.listeningItems.observe(viewLifecycleOwner) { items ->
                if (items != null) {
                    listeningAdapter.submitList(items)
                    loadedSections.add("listening")
                } else {
                    Log.w("DashboardFragment", "No listening items found.")
                }
            }
        }
    }

    private fun loadWritingItems() {
        if (!loadedSections.contains("writing")) {
            dashboardViewModel.writingItems.observe(viewLifecycleOwner) { items ->
                if (items != null) {
                    writingAdapter.submitList(items)
                    loadedSections.add("writing")
                } else {
                    Log.w("DashboardFragment", "No writing items found.")
                }
            }
        }
    }

    private fun loadSpeakingItems() {
        if (!loadedSections.contains("speaking")) {
            dashboardViewModel.speakingItems.observe(viewLifecycleOwner) { items ->
                if (items != null) {
                    speakingAdapter.submitList(items)
                    loadedSections.add("speaking")
                } else {
                    Log.w("DashboardFragment", "No speaking items found.")
                }
            }
        }
    }

    private fun navigateToYouTube(query: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
        startActivity(intent)
    }
}
