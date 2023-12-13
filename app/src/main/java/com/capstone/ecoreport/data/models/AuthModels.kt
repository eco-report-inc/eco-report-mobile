package com.capstone.ecoreport.data.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("nama")
    val nama: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("address")
    val address: String
)

data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String
)

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String
)



