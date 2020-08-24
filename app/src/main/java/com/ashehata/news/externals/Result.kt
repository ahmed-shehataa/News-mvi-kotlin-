package com.ashehata.news.externals

sealed class Result<out T>{
    data class Success<out T>(val data: T) : Result<T>()
    data class Failed(val error: ErrorEntity) : Result<Nothing>()
}