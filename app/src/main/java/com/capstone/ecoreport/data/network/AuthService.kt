package com.capstone.ecoreport.data.network

import retrofit2.http.POST

interface AuthService {
    // Definisikan endpoint-endpoint API di sini
    // Contoh:
    @POST("login")
    suspend fun login(/* tambahkan parameter sesuai kebutuhan */)

    @POST("register")
    suspend fun register(/* tambahkan parameter sesuai kebutuhan */)
}