package com.wolfbytetechnologies.ielts.Networking

import retrofit2.Response
import retrofit2.http.GET

interface IELTSApiService {
    @GET("questions") // Mock endpoint
    suspend fun fetchQuestions(): Response<List<Questions>>
}
