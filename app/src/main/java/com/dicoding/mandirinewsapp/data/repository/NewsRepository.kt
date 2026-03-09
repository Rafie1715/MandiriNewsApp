package com.dicoding.mandirinewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.mandirinewsapp.data.api.NewsInterface
import com.dicoding.mandirinewsapp.data.model.Article
import com.dicoding.mandirinewsapp.data.model.NewsResponse
import com.dicoding.mandirinewsapp.data.paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val apiService: NewsInterface) {

    suspend fun getTopHeadlines(): NewsResponse {
        return apiService.getTopHeadlines()
    }

    fun getAllNewsStream(query: String = "indonesia"): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService, query) }
        ).flow
    }
}