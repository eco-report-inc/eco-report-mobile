package com.capstone.ecoreport.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.auth.AuthRepository
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.models.RegisterRequest
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _isAuthenticated = mutableStateOf(false)
    val isAuthenticated: State<Boolean> = _isAuthenticated

    // Function to handle login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    _isAuthenticated.value = true
                } else {
                    // Handle login error
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
    }

    fun register(username: String, email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.register(RegisterRequest(username, email, password, repeatPassword))

                if (response.isSuccessful) {
                    _isAuthenticated.value = true
                } else {
                    // Handle registration error
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
    }
}