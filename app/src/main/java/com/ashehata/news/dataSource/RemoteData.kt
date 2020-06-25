package com.ashehata.news.dataSource

import BreakingNewsReponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteData {

    @GET("top-headlines")
    fun getBreakingNews(@Query("country") country: String = "us")
            : Flow<BreakingNewsReponse>
}