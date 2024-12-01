package com.wolfbytetechnologies.ielts.ui.dashboard.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.databinding.CustomDialogLayoutBinding
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var mainDashboardItemsRepo: MainDashboardItemsRepo
    private lateinit var dashboardAdapter: DashboardAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.progressBar.isVisible = false
        // Inflate the layout for this fragment
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainDashboardItemsRepo = MainDashboardItemsRepo(requireContext())
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardViewModel.addItemsToDashboard(mainDashboardItemsRepo.getDashboardItems())

        observeDashboardItems()

    }

    private fun observeDashboardItems() {
        val dashBoardItemList = dashboardViewModel.dashboardItems

        dashboardAdapter = DashboardAdapter(dashBoardItemList) { adapterPosition ->
            onClick(adapterPosition)
        }
        binding.rvMainDashboard.apply {
            adapter = dashboardAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        }

    }

    private fun onClick(adapterPosition: Int) {
        when (adapterPosition) {
            0 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            1 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            2 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            3 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            4 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            5 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            6 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            7 -> {
                displayProgressIfSlow()
                //The intent navigation
            }

            8 -> {
                displayProgressIfSlow()
                //The intent navigation
            }
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