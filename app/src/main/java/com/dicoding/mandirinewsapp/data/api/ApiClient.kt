package com.dicoding.mandirinewsapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val instance: NewsInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(NewsInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Converter JSON ke Kotlin Data Class
            .client(client)
            .build()

        retrofit.create(NewsInterface::class.java)
    }
}

