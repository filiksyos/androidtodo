package com.wolfbytetechnologies.ielts.ui.dashboard.adapter

class AdapterProviderImpl : AdapterProvider {
    override fun createDashboardAdapter(onClick: (Int) -> Unit): DashboardAdapter {
        return DashboardAdapter(onClick)
    }
}