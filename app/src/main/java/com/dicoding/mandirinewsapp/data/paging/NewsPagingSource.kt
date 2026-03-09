package com.dicoding.mandirinewsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.mandirinewsapp.data.api.NewsInterface
import com.dicoding.mandirinewsapp.data.model.Article

class NewsPagingSource(
    private val apiService: NewsInterface,
    private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 1

        return try {
            val response = apiService.getAllNews(
                query = query,
                page = position,
                pageSize = params.loadSize
            )
            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}