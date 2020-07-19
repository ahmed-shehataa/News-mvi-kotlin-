package com.ashehata.news.dataSource

import com.ashehata.news.models.breakingNews.BreakingNewsReponse
import com.ashehata.news.models.source.SourcesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteData {

    @GET("top-headlines")
    suspend fun getBreakingNews(@Query("country") country: String = "us")
            : BreakingNewsReponse

    @GET("sources")
    suspend fun getSourcesList(): SourcesResponse
}