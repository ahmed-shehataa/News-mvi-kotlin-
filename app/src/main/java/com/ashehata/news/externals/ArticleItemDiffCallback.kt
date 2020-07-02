package com.ashehata.news.externals

import androidx.recyclerview.widget.DiffUtil
import com.ashehata.news.models.breakingNews.Articles

class ArticleItemDiffCallback : DiffUtil.ItemCallback<Articles>() {

    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }
}