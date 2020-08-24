package com.ashehata.news.externals

sealed class ErrorEntity {
    object Network : ErrorEntity()
    object NotFound : ErrorEntity()
    object Unavailable : ErrorEntity()
    object Unknown : ErrorEntity()
    object AccessDenied : ErrorEntity()
}