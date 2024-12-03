package com.wolfbytetechnologies.ielts.ui.dashboard.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems

class DashboardViewModel : ViewModel() {
    val dashboardItems : MutableList<DashboardItems> = mutableListOf()

    fun addItemsToDashboard(listOfItems : List<DashboardItems>){
        if (dashboardItems.isNullOrEmpty()){
            listOfItems.forEach {
                dashboardItems.add(it)
            }
        }
    }
}
