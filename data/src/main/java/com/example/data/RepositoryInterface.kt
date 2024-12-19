package com.example.data

interface RepositoryInterface {
    fun getReadingItems(): List<DashboardItems>
    fun getListeningItems(): List<DashboardItems>
    fun getWritingItems(): List<DashboardItems>
    fun getSpeakingItems(): List<DashboardItems>
}
