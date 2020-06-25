package com.ashehata.news.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {

    /**
     *
     */
    private val _viewState = MutableLiveData<HomeViewState?>()
    val viewState: LiveData<HomeViewState?> = _viewState
    private fun getCurrentState() = _viewState.value


    init {
        // To init
        _viewState.value = HomeViewState(
            isLoading = true,
            isRefreshing = false,
            data = null,
            error = null
        )
    }

    fun getData() {

    }
}