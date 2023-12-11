package com.capstone.ecoreport.ui.auth

import android.util.Log
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.auth.AuthRepository
import com.capstone.ecoreport.data.models.LoginResponse
import com.capstone.ecoreport.data.models.RegisterRequest
import retrofit2.Response
import java.io.IOException

object LoginViewModel {
    private const val TAG = "LoginViewModel"

    suspend fun performLogin(
        email: String,
        password: String,
        authRepository: AuthRepository,
        onLoginResponse: (Response<LoginResponse>) -> Unit,
        onLoginError: (String) -> Unit,
        onLoading: (Boolean) -> Unit
    ) {
        try {
            onLoading(true)
            val response = authRepository.login(
                LoginRequest(email, password)
            )
            onLoginResponse.invoke(response)

            if (response.isSuccessful) {
                val token = response.body()?.token ?: ""
                authRepository.getAuthPref().saveAuthToken(token)
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
            val errorMessage = "Network error. Please check your internet connection."
            Log.e(TAG, errorMessage, e)
            onLoginError.invoke(errorMessage)
        } catch (e: Exception) {
            val errorMessage = "An unexpected error occurred during login."
            Log.e(TAG, errorMessage, e)
            onLoginError.invoke(errorMessage)
        } finally {
            onLoading(false)
        }
    }
}
    object RegisterViewModel {
    private const val TAG = "RegisterViewModel"

    suspend fun performRegistration(
        username: String,
        email: String,
        password: String,
        repeatPassword: String,
        authRepository: AuthRepository,
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
            val response = authRepository.register(
                RegisterRequest(username, email, password, repeatPassword)
            )
            if (response.isSuccessful) {
                // Registrasi berhasil, panggil callback
                onRegisterSuccess.invoke()
            } else {
                // Registrasi gagal, panggil callback dengan pesan error
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



