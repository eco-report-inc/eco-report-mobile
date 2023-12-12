package com.capstone.ecoreport.data.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = if (request.header("No-Authentication") == null && token.isNotEmpty()) {
            val finalToken = "Bearer $token"
            request.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        } else {
            request
        }
        return chain.proceed(authenticatedRequest)
    }
}