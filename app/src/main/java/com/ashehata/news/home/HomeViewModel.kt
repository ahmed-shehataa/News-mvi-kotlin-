package com.ashehata.news.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ashehata.news.base.BaseViewModel
import com.ashehata.news.externals.ErrorEntity
import com.ashehata.news.externals.ErrorType
import com.ashehata.news.externals.Result
import com.ashehata.news.models.breakingNews.Articles
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.*

class HomeViewModel @ViewModelInject constructor(private val useCase: HomeUseCase) :
    BaseViewModel() {

    private val _stateChannel = MutableStateFlow(HomeViewState(isLoading = true))
    val stateChannel: StateFlow<HomeViewState?> = _stateChannel
    val intentChannel = Channel<HomeIntent>(CONFLATED)
    private fun getCurrentState() = _stateChannel.value


    init {

        viewModelScope.launch {
            // Start intent listening
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is HomeIntent.RequestNews -> getData()
                    is HomeIntent.RequestRefresh -> setRefreshing()

                }
            }
        }
        //...
    }

    private suspend fun getData() {
        // Collect the data
        getSources()
        getArticles()

    }

    private suspend fun getArticles() {
        viewModelScope.launch {
            val result = useCase.getNews()
            when (result) {
                is Result.Success -> showData(result.data)
                is Result.Failed -> showError(result.error)
            }
        }

    }

    private fun showError(error: ErrorEntity) {
        _stateChannel.value = getCurrentState().copy(
            isLoading = false,
            isRefreshing = false,
            error = error,
            listArticles = null
        )
    }

    private fun showData(data: List<Articles>) {
        _stateChannel.value = getCurrentState().copy(
            isLoading = false,
            isRefreshing = false,
            error = null,
            listArticles = data
        )
    }


    private suspend fun getSources() {
        useCase.getSources().catch {
            catchError()

        }.collect {
            _stateChannel.value = getCurrentState().copy(
                isLoading = false,
                isRefreshing = false,
                error = null,
                listSources = it
            )

        }
    }

    private fun catchError() {
        _stateChannel.value = getCurrentState().copy(
            isLoading = false,
            isRefreshing = false,
            error = null
        )
    }

    private suspend fun setRefreshing() {
        // Update current state with loading bar
        _stateChannel.value = getCurrentState().copy(
            isRefreshing = true,
            isLoading = false
        )

        if (getCurrentState().listSources.isNullOrEmpty()) {
            getSources()
        }

        if (getCurrentState().listArticles.isNullOrEmpty()) {
            getArticles()
        }

        _stateChannel.value = getCurrentState().copy(
            isRefreshing = false,
            isLoading = false
        )

    }

    override fun onCleared() {
        super.onCleared()
        // release memory
        intentChannel.close()
    }
}