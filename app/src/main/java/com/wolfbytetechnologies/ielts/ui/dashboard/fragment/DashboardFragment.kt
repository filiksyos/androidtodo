package com.wolfbytetechnologies.ielts.ui.dashboard.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    // Inject dependencies using Koin
    private val mainDashboardItemsRepo: MainDashboardItemsRepo by inject()
    private val dashboardViewModel: DashboardViewModel by viewModel()

    // Map adapter positions to YouTube search queries
    private val dashboardActions = mapOf(
        0 to { openYouTubeSearch("IELTS Reading Tips and Tricks") },
        1 to { openYouTubeSearch("IELTS Listening Tips and Tricks") },
        2 to { openYouTubeSearch("IELTS Writing Tips and Tricks") },
        3 to { openYouTubeSearch("IELTS Speaking Tips and Tricks") },
        4 to { openYouTubeSearch("IELTS Vocabulary Building") },
        5 to { openYouTubeSearch("IELTS Grammar Lessons") },
        6 to { openYouTubeSearch("IELTS Test Day Tips") },
        7 to { openYouTubeSearch("IELTS General Training Tips") },
        8 to { openYouTubeSearch("IELTS Academic Training Tips") }
    )

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?,
    ): android.view.View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.progressBar.isVisible = false
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use injected dependencies
        dashboardViewModel.addItemsToDashboard(mainDashboardItemsRepo.getDashboardItems())

        observeDashboardItems()
    }

    private fun observeDashboardItems() {
        val dashBoardItemList = dashboardViewModel.dashboardItems

        val dashboardAdapter = DashboardAdapter(dashBoardItemList) { adapterPosition ->
            handleDashboardAction(adapterPosition)
        }

        binding.rvMainDashboard.apply {
            adapter = dashboardAdapter
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun handleDashboardAction(adapterPosition: Int) {
        // Execute the action defined in the dashboardActions map, if available
        dashboardActions[adapterPosition]?.invoke() ?: run {
            Toast.makeText(requireContext(), "No action defined for this item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openYouTubeSearch(query: String) {
        val link = "https://www.youtube.com/results?search_query=${Uri.encode(query)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))

        // Check if the intent is resolvable
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "No app available to open the link", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(
            requireContext(), getString(R.string.ielts_preparation_actionbar)
        )
        dashboardViewModel.saveButtonVisibility(requireContext(), false)
    }
}