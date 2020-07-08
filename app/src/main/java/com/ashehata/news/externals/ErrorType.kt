package com.ashehata.news.externals

sealed class ErrorType {
    object NoConnection : ErrorType()
    class Error(var message: String) : ErrorType()
    object NoError : ErrorType()
}