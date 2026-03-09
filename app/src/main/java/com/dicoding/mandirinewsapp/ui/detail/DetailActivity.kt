package com.dicoding.mandirinewsapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mandirinewsapp.data.model.Article
import com.dicoding.mandirinewsapp.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        if (article != null) {
            setupUI(article)
        } else {
            finish()
        }
    }

    private fun setupUI(article: Article) {
        binding.apply {
            tvDetailTitle.text = article.title
            tvDetailAuthor.text = article.author ?: article.source?.name ?: "Unknown"
            tvDetailDate.text = formatDate(article.publishedAt)
            tvDetailDescription.text = article.description
            tvDetailContent.text = article.content

            Glide.with(this@DetailActivity)
                .load(article.urlToImage)
                .into(ivDetailImage)

            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            btnReadMore.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }
    }

    private fun formatDate(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            dateString
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}