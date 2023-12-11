package com.capstone.ecoreport.data.auth

import com.capstone.ecoreport.data.api.ApiService
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.models.LoginResponse
import com.capstone.ecoreport.data.models.RegisterRequest
import com.capstone.ecoreport.data.models.RegisterResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

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
