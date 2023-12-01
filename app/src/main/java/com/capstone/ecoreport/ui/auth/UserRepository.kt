package com.capstone.ecoreport.ui.auth

import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.postRegister(
            name = registerRequest.nama,
            email = registerRequest.email,
            password = registerRequest.password,
            address = registerRequest.address
        )
    }
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.postLogin(
            email = loginRequest.email,
            password = loginRequest.password
        )
    }
}
