package com.ashehata.news.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ashehata.news.base.BaseViewModel
import com.ashehata.news.externals.ErrorType
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
        useCase.getNews().catch {
            catchError()

        }.collect {
            _stateChannel.value = getCurrentState().copy(
                isLoading = false,
                isRefreshing = false,
                error = ErrorType.NoError,
                listArticles = it
            )
        }
    }

    private suspend fun getSources() {
        useCase.getSources().catch {
            catchError()

        }.collect {
            _stateChannel.value = getCurrentState().copy(
                isLoading = false,
                isRefreshing = false,
                error = ErrorType.NoError,
                listSources = it
            )

        }
    }

    private fun catchError() {
        _stateChannel.value = getCurrentState().copy(
            isLoading = false,
            isRefreshing = false,
            error = ErrorType.NoConnection
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