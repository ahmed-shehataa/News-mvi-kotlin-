package com.ashehata.news.home

import com.ashehata.news.dataSource.RemoteData
import com.ashehata.news.models.breakingNews.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
}