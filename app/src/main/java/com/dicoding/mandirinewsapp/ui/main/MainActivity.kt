package com.dicoding.mandirinewsapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mandirinewsapp.data.api.ApiClient
import com.dicoding.mandirinewsapp.data.model.Article
import com.dicoding.mandirinewsapp.data.repository.NewsRepository
import com.dicoding.mandirinewsapp.databinding.ActivityMainBinding
import com.dicoding.mandirinewsapp.ui.adapter.HeadlineAdapter
import com.dicoding.mandirinewsapp.ui.adapter.NewsAdapter
import com.dicoding.mandirinewsapp.ui.detail.DetailActivity
import com.dicoding.mandirinewsapp.utils.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headlineAdapter: HeadlineAdapter
    private lateinit var newsAdapter: NewsAdapter

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(NewsRepository(ApiClient.instance))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapters()
        observeData()
    }

    private fun setupAdapters() {
        headlineAdapter = HeadlineAdapter { article ->
            navigateToDetail(article)
        }
        binding.rvHeadlines.apply {
            adapter = headlineAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        newsAdapter = NewsAdapter { article ->
            navigateToDetail(article)
        }
        binding.rvAllNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.headlines.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            headlineAdapter.submitList(state.data.articles)
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@MainActivity, state.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllNews().collectLatest { pagingData ->
                    newsAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun navigateToDetail(article: Article) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ARTICLE, article)
        }
        startActivity(intent)
    }
}