package com.ashehata.news.home

import com.ashehata.news.externals.ErrorType
import com.ashehata.news.models.breakingNews.Articles
import com.ashehata.news.models.breakingNews.BreakingNewsReponse

data class HomeViewState(
    var data: List<Articles>?,
    var isLoading: Boolean?,
    var isRefreshing: Boolean?,
    var error: ErrorType?
)