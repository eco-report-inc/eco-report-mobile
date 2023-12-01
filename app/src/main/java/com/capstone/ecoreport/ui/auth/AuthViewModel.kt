package com.capstone.ecoreport.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    onLoginSuccess.invoke()
                } else {
                    onLoginError.invoke("Login failed. Check your email and password.")
                }
            } catch (e: Exception) {
                onLoginError.invoke("An error occurred during login.")
            }
        }
    }

    fun register(username: String, email: String, password: String, repeatPassword: String,
                 onRegisterSuccess: () -> Unit, onRegisterError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.register(RegisterRequest(username, email, password, repeatPassword))
                if (response.isSuccessful) {
                    onRegisterSuccess.invoke()
                } else {
                    onRegisterError.invoke("Registration failed. Please try again.")
                }
            } catch (e: Exception) {
                onRegisterError.invoke("An error occurred during registration.")
            }
        }
    }
}

