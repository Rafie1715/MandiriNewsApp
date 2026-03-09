package com.dicoding.mandirinewsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mandirinewsapp.data.model.Article
import com.dicoding.mandirinewsapp.data.model.NewsResponse
import com.dicoding.mandirinewsapp.data.repository.NewsRepository
import com.dicoding.mandirinewsapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _headlines = MutableStateFlow<UiState<NewsResponse>>(UiState.Loading)
    val headlines: StateFlow<UiState<NewsResponse>> = _headlines.asStateFlow()

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch {
            _headlines.value = UiState.Loading
            try {
                val response = repository.getTopHeadlines()
                _headlines.value = UiState.Success(response)
            } catch (e: Exception) {
                _headlines.value = UiState.Error(e.message ?: "Terjadi kesalahan yang tidak diketahui")
            }
        }
    }

    fun getAllNews(): Flow<PagingData<Article>> {
        return repository.getAllNewsStream()
            .cachedIn(viewModelScope)
    }
}