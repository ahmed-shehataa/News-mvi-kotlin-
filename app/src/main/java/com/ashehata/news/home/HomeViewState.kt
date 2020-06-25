package com.ashehata.news.home

import BreakingNewsReponse
import com.ashehata.news.externals.ErrorType

data class HomeViewState(
    var data: BreakingNewsReponse?,
    var isLoading: Boolean?,
    var isRefreshing: Boolean?,
    var error: ErrorType?
)