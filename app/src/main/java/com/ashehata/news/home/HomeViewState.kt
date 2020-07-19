package com.ashehata.news.home

import com.ashehata.news.externals.ErrorType
import com.ashehata.news.models.breakingNews.Articles
import com.ashehata.news.models.breakingNews.BreakingNewsReponse
import com.ashehata.news.models.source.Sources

data class HomeViewState(
    var listArticles: List<Articles>? = null,
    var listSources: List<Sources>? = null,
    var isLoading: Boolean? = false,
    var isRefreshing: Boolean? = false,
    var error: ErrorType? = null
)