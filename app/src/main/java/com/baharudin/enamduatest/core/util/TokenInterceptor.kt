package com.baharudin.enamduatest.core.util

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor constructor(
    private val accessToken : String,
    private val tokenType : String
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $accessToken")
            .build()
        return chain.proceed(request)
    }
}