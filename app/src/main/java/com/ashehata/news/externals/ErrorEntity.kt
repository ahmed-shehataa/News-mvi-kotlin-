package com.ashehata.news.externals

sealed class ErrorEntity {
    class NotFound(val message: String) : ErrorEntity()
    object Network : ErrorEntity()
    object Unavailable : ErrorEntity()
    object Unknown : ErrorEntity()
    object AccessDenied : ErrorEntity()
}