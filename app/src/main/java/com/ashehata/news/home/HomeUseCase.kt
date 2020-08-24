package com.ashehata.news.home

import com.ashehata.news.externals.ErrorType
import com.ashehata.news.models.breakingNews.Articles
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend fun getNews() = repository.getNews()
        /**
         * Here u can filter or map or use any operator
         * corresponding to your use case
         */


    suspend fun getSources() = repository.getSources()
        .map {
            return@map it.sortedBy {
                it.name
            }
        }
        /**
         * Here u can filter or map or use any operator
         * corresponding to your use case
         */
        .flowOn(Dispatchers.IO)

}