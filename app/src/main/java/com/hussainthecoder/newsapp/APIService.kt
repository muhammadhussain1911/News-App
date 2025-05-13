package com.hussainthecoder.newsapp

import com.hussainthecoder.newsapp.models.NewsModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("top-headlines")
    suspend fun apiCalling(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String
    ): Response<NewsModels>
}
