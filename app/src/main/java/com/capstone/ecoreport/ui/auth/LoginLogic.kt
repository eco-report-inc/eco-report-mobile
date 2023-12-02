package com.capstone.ecoreport.ui.auth

import android.util.Log
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.repository.UserRepository
import java.io.IOException

object LoginLogic {
    private const val TAG = "LoginLogic"

    suspend fun performLogin(
        email: String,
        password: String,
        userRepository: UserRepository,
        onLoginSuccess: () -> Unit,
        onLoginError: (String) -> Unit,
        onLoading: (Boolean) -> Unit
    ) {
        try {
            onLoading(true)
            val response = userRepository.login(
                LoginRequest(email, password)
            )
            if (response.isSuccessful) {
                onLoginSuccess.invoke()
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Invalid credentials. Please check your email and password."
                    404 -> "User not found. Please check your email."
                    else -> "Login failed. ${response.message()}"
                }
                Log.e(TAG, errorMessage)
                onLoginError.invoke(errorMessage)
            }
        } catch (e: IOException) {
            // Kesalahan jaringan
            val errorMessage = "Network error. Please check your internet connection."
            Log.e(TAG, errorMessage, e)
            onLoginError.invoke(errorMessage)
        } catch (e: Exception) {
            // Kesalahan umum
            val errorMessage = "An unexpected error occurred during login."
            Log.e(TAG, errorMessage, e)
            onLoginError.invoke(errorMessage)
        } finally {
            onLoading(false)
        }
    }
}


