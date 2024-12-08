package com.wolfbytetechnologies.ielts.Networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//The app doesn't implement API calls yet.

object NetworkModule {
    private const val BASE_URL = "https://mock.api.example.com/" // Placeholder URL

    private val client = OkHttpClient.Builder()
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
