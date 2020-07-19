package com.ashehata.news.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashehata.news.R
import com.ashehata.news.externals.ArticleItemDiffCallback
import com.ashehata.news.models.breakingNews.Articles
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.root_article.view.*
import javax.inject.Inject

/**
 * ListAdapter<Articles, HomeAdapter.ArticleViewHolder>(ArticleItemDiffCallback())
 */
class HomeAdapter @Inject constructor(private val loadImage: RequestManager) :
    ListAdapter<Articles, HomeAdapter.ArticleViewHolder>(ArticleItemDiffCallback<Articles>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(loadImage, LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        //getItem(position)
        holder.bind(getItem(position))
    }

    class ArticleViewHolder(
        private val loadImage: RequestManager,
        inflater: LayoutInflater, parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.root_article, parent, false)) {

        private var title: TextView = itemView.tv_title
        private var image: ImageView = itemView.iv_image

        fun bind(article: Articles) {
            title.text = article.title


            Log.i("glideAddress", System.identityHashCode(loadImage).toString())
            loadImage
                .load(article.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.shape_placeholder)
                .into(image);

        }

    }

}