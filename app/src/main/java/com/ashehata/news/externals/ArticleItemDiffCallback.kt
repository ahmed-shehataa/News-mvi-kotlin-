package com.ashehata.news.externals

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.ashehata.news.models.breakingNews.Articles

class ArticleItemDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}