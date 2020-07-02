package com.ashehata.news.home

import com.ashehata.news.externals.ErrorType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeUseCase @Inject constructor(private val repository: HomeRepository) {

    fun getNews(
        viewModelScope: CoroutineScope,
        currentViewState: HomeViewState?,
        updateState: (HomeViewState?) -> Unit
    ) {

        viewModelScope.launch() {
            try {
                repository.getNews()
                    .catch {
                        // Update current state with error message
                        updateState(
                            currentViewState?.copy(
                                isLoading = false,
                                isRefreshing = false,
                                data = null,
                                error = ErrorType.Error(it.localizedMessage!!)
                            )
                        )
                        //...

                    }
                    .collect {
                        // Update current state with data
                        updateState(
                            currentViewState?.copy(
                                isLoading = false,
                                data = it,
                                //error = ErrorType.Error(it.status),
                                isRefreshing = false
                            )
                        )
                        //...
                    }

            } catch (e: Exception) {
                // Update current state with error message
                updateState(
                    currentViewState?.copy(
                        isLoading = false,
                        data = null,
                        error = ErrorType.NoConnection(),
                        isRefreshing = false
                    )
                )
                //...
            }
        }
    }
}