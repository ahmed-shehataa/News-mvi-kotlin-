package com.ashehata.news.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashehata.news.R
import com.ashehata.news.externals.ArticleItemDiffCallback
import com.ashehata.news.models.breakingNews.Articles
import com.ashehata.news.models.source.Sources
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.root_article.view.*
import kotlinx.android.synthetic.main.root_source.view.*
import javax.inject.Inject

class SourceAdapter :
    ListAdapter<Sources, SourceAdapter.SourcesViewHolder>(ArticleItemDiffCallback<Sources>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceAdapter.SourcesViewHolder {
        return SourceAdapter.SourcesViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: SourceAdapter.SourcesViewHolder, position: Int) {
        //getItem(position)
        holder.bind(getItem(position))
    }

    class SourcesViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.root_source, parent, false)) {

        private var name: TextView = itemView.tv_name

        fun bind(sources: Sources) {
            name.text = sources.name
        }

    }

}