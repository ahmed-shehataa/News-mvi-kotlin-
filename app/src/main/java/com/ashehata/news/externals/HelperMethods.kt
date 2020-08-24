package com.ashehata.news.externals

import android.view.View
import com.bumptech.glide.load.HttpException
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

fun View.showMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend ()-> T): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException ->
                    Result.Failed(ErrorEntity.Network)
                is HttpException -> {
                    Result.Failed(ErrorEntity.Unavailable)
                }
                else -> {
                    Result.Failed(ErrorEntity.Unknown)
                }
            }
        }
    }
}