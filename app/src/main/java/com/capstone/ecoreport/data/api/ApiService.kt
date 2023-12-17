package com.capstone.ecoreport.data.api

import com.capstone.ecoreport.data.models.LoginResponse
import com.capstone.ecoreport.data.models.RegisterResponse
import com.capstone.ecoreport.data.models.ReportResponse
import com.capstone.ecoreport.data.models.UploadPhotoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("user/register")
    suspend fun postRegister(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @Multipart
    @POST("report/report")
    suspend fun postAddReport(
        @Part("nama_tempat") placeName: String,
        @Part("lang") latitude: String,
        @Part("long") longitude: String,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part
    ): Response<ReportResponse>

    // Get All Reports
    @GET("report/report")
    suspend fun getAllReports(): Response<ReportResponse>

    @GET("report/report/{report_id}")
    suspend fun getSingleReport(@Path("report_id") reportId: String): Response<ReportResponse>

    @GET("report/report")
    suspend fun getAllReportsWithQuery(
        @Query("long") longitude: String,
        @Query("lang") latitude: String
    ): Response<ReportResponse>

    @PATCH("user/upload-photo")
    @Multipart
    suspend fun uploadProfilePhoto(
        @Part photo: MultipartBody.Part
    ): Response<UploadPhotoResponse>
}

