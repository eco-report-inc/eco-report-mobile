package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.ecoreport.data.LoginResult
import com.capstone.ecoreport.data.RegisterResult
import com.capstone.ecoreport.data.auth.AuthRepository
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.models.RegisterRequest
import kotlinx.coroutines.Dispatchers
class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun register(name: String, email: String, password: String, address: String): LiveData<RegisterResult> = liveData(Dispatchers.IO) {
        emit(RegisterResult.Loading)
        try {
            val response = authRepository.register(RegisterRequest(name, email, password, address))

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(RegisterResult.Success(responseBody))
                } else {
                    emit(RegisterResult.Error("Unexpected response from server"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    emit(RegisterResult.Error(errorBody))
                } else {
                    emit(RegisterResult.Error("Registration failed. Please try again."))
                }
            }
        } catch (e: Exception) {
            emit(RegisterResult.Error(e.message ?: "Unknown error occurred"))
        }
    }
    fun login(email: String, password: String): LiveData<LoginResult> = liveData(Dispatchers.IO) {
        emit(LoginResult.loading)
        try {
            val response = authRepository.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(LoginResult.Success(responseBody))
                } else {
                    emit(LoginResult.Error("Unexpected response from server."))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    emit(LoginResult.Error(errorBody))
                } else {
                    emit(LoginResult.Error("Login failed. Please try again."))
                }
            }
        } catch (e: Exception) {
            emit(LoginResult.Error(e.message ?: "Unknown error occurred."))
        }
    }
}
