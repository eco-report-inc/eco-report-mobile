package com.capstone.ecoreport.ui.auth

import com.capstone.ecoreport.data.api.ApiConfig
import com.capstone.ecoreport.data.models.RegisterRequest
import com.capstone.ecoreport.data.repository.UserRepository

object RegisterLogic {
    suspend fun performRegistration(
        username: String,
        email: String,
        password: String,
        repeatPassword: String,
        onRegisterSuccess: () -> Unit,
        onRegisterError: (String) -> Unit
    ) {
        // Validasi password
        if (password != repeatPassword) {
            onRegisterError.invoke("Passwords do not match.")
            return
        }
        try {
            val response = UserRepository(ApiConfig.createApiService()).register(
                RegisterRequest(username, email, password, repeatPassword)
            )
            if (response.isSuccessful) {
                onRegisterSuccess.invoke()
            } else {
                onRegisterError.invoke("Registration failed. Please try again.")
            }
        } catch (e: Exception) {
            onRegisterError.invoke("An error occurred during registration. Please check your internet connection.")
        }
    }
}
