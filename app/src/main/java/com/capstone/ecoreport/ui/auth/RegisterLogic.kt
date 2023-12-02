package com.capstone.ecoreport.ui.auth

import android.util.Log
import com.capstone.ecoreport.data.models.RegisterRequest
import com.capstone.ecoreport.data.repository.UserRepository
import java.io.IOException

object RegisterLogic {
    private const val TAG = "RegisterLogic"

    suspend fun performRegistration(
        username: String,
        email: String,
        password: String,
        repeatPassword: String,
        userRepository: UserRepository,
        onRegisterSuccess: () -> Unit,
        onRegisterError: (String) -> Unit,
        onLoading: (Boolean) -> Unit
    ) {
        // Validasi password
        if (password != repeatPassword) {
            onRegisterError.invoke("Passwords do not match.")
            return
        }
        try {
            onLoading(true)
            val response = userRepository.register(
                RegisterRequest(username, email, password, repeatPassword)
            )
            if (response.isSuccessful) {
                onRegisterSuccess.invoke()
            } else {
                onRegisterError.invoke("Registration failed. Please try again.")
            }
        } catch (e: IOException) {
            // Kesalahan jaringan
            val errorMessage = "Network error. Please check your internet connection."
            Log.e(TAG, errorMessage, e)
            onRegisterError.invoke(errorMessage)
        } catch (e: Exception) {
            // Kesalahan umum
            val errorMessage = "An error occurred during registration. Please check your internet connection."
            Log.e(TAG, errorMessage, e)
            onRegisterError.invoke(errorMessage)
        } finally {
            onLoading(false)
        }
    }
}