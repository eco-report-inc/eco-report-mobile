package com.capstone.ecoreport.ui.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
