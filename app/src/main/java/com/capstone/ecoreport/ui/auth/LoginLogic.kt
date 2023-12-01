package com.capstone.ecoreport.ui.auth

import android.util.Log
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.repository.UserRepository

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
                val errorMessage = "Login failed. ${response.message()}"
                Log.e(TAG, errorMessage)
                onLoginError.invoke(errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = "An error occurred during login. Please check your internet connection."
            Log.e(TAG, errorMessage, e)
            onLoginError.invoke(errorMessage)
        } finally {
            onLoading(false)
        }
    }
}


