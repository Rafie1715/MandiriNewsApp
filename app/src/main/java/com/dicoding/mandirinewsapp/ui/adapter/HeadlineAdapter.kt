package com.dicoding.mandirinewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mandirinewsapp.data.model.Article
import com.dicoding.mandirinewsapp.databinding.ItemHeadlineBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class HeadlineAdapter(private val onItemClick: (Article) -> Unit) :
    ListAdapter<Article, HeadlineAdapter.HeadlineViewHolder>(ARTICLE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val binding = ItemHeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    inner class HeadlineViewHolder(private val binding: ItemHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                tvHeadlineTitle.text = article.title
                tvHeadlineSource.text = article.source?.name ?: "Unknown Source"
                tvHeadlineDate.text = formatDate(article.publishedAt)

                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .centerCrop()
                    .into(ivHeadlineImage)

                root.setOnClickListener {
                    onItemClick(article)
                }
            }
        }

        private fun formatDate(dateString: String?): String {
            if (dateString == null) return ""
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = inputFormat.parse(dateString)
                val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
                date?.let { outputFormat.format(it) } ?: ""
            } catch (e: Exception) {
                dateString
            }
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}