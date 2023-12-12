package com.capstone.ecoreport.data.auth

import com.capstone.ecoreport.data.api.ApiService
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.models.LoginResponse
import com.capstone.ecoreport.data.models.RegisterRequest
import com.capstone.ecoreport.data.models.RegisterResponse
import com.capstone.ecoreport.ui.auth.AuthManager
import retrofit2.Response

class AuthRepository(private val apiService: ApiService, private val authManager: AuthManager) {

    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.postRegister(
            name = registerRequest.nama,
            email = registerRequest.email,
            password = registerRequest.password,
            address = registerRequest.address
        )
    }

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        val response = apiService.postLogin(
            email = loginRequest.email,
            password = loginRequest.password
        )

        if (response.isSuccessful) {
            val token = response.body()?.token.orEmpty()
            authManager.saveAuthToken(token)
        }

        return response
    }
}
