package com.breens.mvvmlivescorestarter.util

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", ACCESS_TOKEN)
            .build()
        return chain.proceed(request)
    }
}