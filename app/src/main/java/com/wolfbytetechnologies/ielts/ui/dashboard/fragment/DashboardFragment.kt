package com.wolfbytetechnologies.ielts.ui.dashboard.fragment

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

    // Map adapter positions to actions (currently placeholders)
    private val dashboardActions = mapOf(
        0 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        1 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        2 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        3 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        4 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        5 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        6 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        7 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ },
        8 to { displayProgressIfSlow() /* TODO: Add YouTube intent logic */ }
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

    private fun displayProgressIfSlow() {
        binding.progressBar.isVisible =
            Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == 27 || Build.VERSION.SDK_INT == 26
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(
            requireContext(), getString(R.string.ielts_preparation_actionbar)
        )
        dashboardViewModel.saveButtonVisibility(requireContext(), false)
    }
}
