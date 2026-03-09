package com.dicoding.mandirinewsapp.data.api

import com.dicoding.mandirinewsapp.BuildConfig
import com.dicoding.mandirinewsapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    companion object {
        const val API_KEY = BuildConfig.NEWS_API_KEY
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") query: String = "indonesia",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}