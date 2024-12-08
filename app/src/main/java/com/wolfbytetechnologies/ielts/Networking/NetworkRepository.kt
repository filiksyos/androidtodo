package com.wolfbytetechnologies.ielts.Networking

//The app doesn't implement API calls yet.
class NetworkRepository(private val apiService: IELTSApiService) {

    suspend fun getQuestions(): ApiResponse<List<Questions>> {
        return try {
            val response = apiService.fetchQuestions()
            if (response.isSuccessful) {
                ApiResponse.Success(response.body() ?: emptyList())
            } else {
                ApiResponse.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Exception: ${e.localizedMessage}")
        }
    }
}
