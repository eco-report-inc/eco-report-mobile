package com.capstone.ecoreport.ui.auth

import retrofit2.Response

class AuthRepository {

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return ApiClient.apiService.register(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return ApiClient.apiService.login(request)
    }
}

