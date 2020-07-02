package com.ashehata.news.externals

import okhttp3.Interceptor
import okhttp3.Response

class AddKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("X-Api-Key", API_KEY).build()

       return chain.proceed(newRequest)
    }
}