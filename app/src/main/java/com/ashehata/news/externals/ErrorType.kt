package com.ashehata.news.externals

sealed class ErrorType {
    class NoConnection() : ErrorType()
    class Error(var message: String) : ErrorType()
}