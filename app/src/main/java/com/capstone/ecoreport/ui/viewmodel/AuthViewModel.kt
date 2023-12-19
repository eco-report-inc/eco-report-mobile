package com.capstone.ecoreport.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.data.auth.AuthRepository
import com.capstone.ecoreport.data.models.LoginRequest
import com.capstone.ecoreport.data.models.RegisterRequest
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val authManager: AuthManager,
) : ViewModel() {

    private val _isAuthenticated = mutableStateOf(authManager.isLoggedIn())
    val isAuthenticated: State<Boolean> = _isAuthenticated

    private val _authToken = mutableStateOf(authManager.getAuthToken())

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    _isAuthenticated.value = true
                    _authToken.value = response.body()?.token
                    authManager.saveAuthToken(response.body()?.token ?: "")
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
                val response = authRepository.register(
                    RegisterRequest(username, email, password, repeatPassword)
                )
                if (response.isSuccessful) {
                    _isAuthenticated.value = true
                    authManager.saveAuthToken(response.body()?.token ?: "")
                } else {
                    // Handle registration error
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
    }
    fun logout() {
        _isAuthenticated.value = false
        _authToken.value = null
        authManager.clearAuthToken()
    }
}