package com.capstone.ecoreport.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.ecoreport.data.network.AuthRepository


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // Implementasi fungsi-fungsi ViewModel untuk login dan register
    suspend fun login(/* tambahkan parameter sesuai kebutuhan */) {
        authRepository.login(/* tambahkan parameter sesuai kebutuhan */)
    }

    suspend fun register(/* tambahkan parameter sesuai kebutuhan */) {
        authRepository.register(/* tambahkan parameter sesuai kebutuhan */)
    }
}