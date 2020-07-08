package com.ashehata.news.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ashehata.news.base.BaseViewModel
import com.ashehata.news.externals.ErrorType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val useCase: HomeUseCase) :
    BaseViewModel() {

    private val _stateChannel = MutableStateFlow<HomeViewState?>(null)
    val stateChannel: StateFlow<HomeViewState?> = _stateChannel
    val intentChannel = Channel<HomeIntent>(CONFLATED)
    private fun getState() = _stateChannel.value


    init {

        viewModelScope.launch {
            // Send the current model
            _stateChannel.value = HomeViewState(isLoading = true)
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
        useCase.getNews().catch {
            _stateChannel.value = getState()?.copy(
                isLoading = false,
                error = ErrorType.NoConnection
            )

        }.collect {
            _stateChannel.value = getState()?.copy(
                isLoading = false,
                error = ErrorType.NoError,
                data = it
            )

        }
    }

    private suspend fun setRefreshing() {
        _stateChannel.value = getState()?.copy(
            isRefreshing = true,
            isLoading = false
        )
        // simulate get something
        delay(2000)
        _stateChannel.value = getState()?.copy(
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