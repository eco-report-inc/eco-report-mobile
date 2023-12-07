package com.capstone.ecoreport.data.api

import com.capstone.ecoreport.data.models.LoginResponse
import com.capstone.ecoreport.data.models.RegisterResponse
import com.capstone.ecoreport.data.models.ReportResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("report/report/{reportId}")
    suspend fun getSingleReport(
        @Path("reportId") reportId: String
    ): Response<ReportResponse>

    @GET("report/report")
    suspend fun getAllReports(): Response<ReportResponse>

    @GET("report/report")
    suspend fun getAllReportsWithQueryString(
        @Query("long") longitude: String,
        @Query("lang") latitude: String
    ): Response<ReportResponse>
}

