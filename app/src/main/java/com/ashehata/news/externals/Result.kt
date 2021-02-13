package com.ashehata.news.externals

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse = ErrorResponse("")) :
        Result<Nothing>()

    object NetworkError : Result<Nothing>()
}