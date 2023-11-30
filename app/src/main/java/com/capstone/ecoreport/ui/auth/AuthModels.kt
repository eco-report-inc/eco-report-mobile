package com.capstone.ecoreport.ui.auth

data class RegisterRequest(
    val nama: String,
    val email: String,
    val password: String,
    val address: String
)

data class RegisterResponse(
    val message: String,
    val token: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String
)
