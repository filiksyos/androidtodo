package com.wolfbytetechnologies.ielts.Networking

//The app doesn't implement API calls yet.
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}