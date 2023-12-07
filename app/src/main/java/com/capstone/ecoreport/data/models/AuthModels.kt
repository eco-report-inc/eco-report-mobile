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
data class ImageResponse(
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("report_id")
    val reportId: String,
    @SerializedName("gambar")
    val imageUrl: String
)
data class ReportData(
    @SerializedName("report_id")
    val reportId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("nama_tempat")
    val placeName: String,
    @SerializedName("lang")
    val latitude: String,
    @SerializedName("long")
    val longitude: String,
    @SerializedName("jumlah_laporan")
    val reportCount: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("Image")
    val images: List<ImageResponse>
)

data class ReportResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val reportData: List<ReportData>
)


