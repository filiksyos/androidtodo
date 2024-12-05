package com.wolfbytetechnologies.ielts.ui.dashboard.adapter

interface AdapterProvider {
    fun createDashboardAdapter(onClick: (Int) -> Unit): DashboardAdapter
}