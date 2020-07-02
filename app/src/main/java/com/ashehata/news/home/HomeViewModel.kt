package com.ashehata.news.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ashehata.news.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @ViewModelInject constructor(private val useCase: HomeUseCase) : BaseViewModel() {

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
        //getData()
    }

    fun getData() {
        useCase.getNews(viewModelScope = viewModelScope,
            currentViewState = getCurrentState(),
            updateState = {
                // Push state to mutable live data
                _viewState.postValue(it)
            }
        )
    }

    fun serRefreshing() {
        viewModelScope.launch {
            delay(2000)
            _viewState.value = getCurrentState()?.copy(
                isRefreshing = false,
                isLoading = false
            )
        }
    }
}