package com.ashehata.news.home

import com.ashehata.news.dataSource.RemoteData
import com.ashehata.news.models.breakingNews.Articles
import com.ashehata.news.models.source.Sources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeRepository @Inject constructor(private val remoteData: RemoteData) {

    suspend fun getNews() : Flow<List<Articles>> {
        return flow {
            // Get the response from api call
            val response = remoteData.getBreakingNews()

            // Emit list of articles
            emit(response.articles)

        }
    }

    suspend fun getSources() : Flow<List<Sources>> {
        return flow {
            // Get the response from api call
            val response = remoteData.getSourcesList()

            // Emit list of articles
            emit(response.sources)
        }
    }

}