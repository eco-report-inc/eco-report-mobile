package com.capstone.ecoreport.data.Report

import com.capstone.ecoreport.data.api.ApiService
import com.capstone.ecoreport.data.models.ReportResponse
import okhttp3.MultipartBody
import retrofit2.Response

class ReportManager(private val apiService: ApiService) {
    suspend fun postAddReport(
        placeName: String,
        latitude: String,
        longitude: String,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part
    ): Response<ReportResponse> {
        return apiService.postAddReport(placeName, latitude, longitude, image1, image2)
    }
    suspend fun getAllReports(): Response<ReportResponse> {
        return apiService.getAllReports()
    }
    suspend fun getSingleReport(reportId: String): Response<ReportResponse> {
        return apiService.getSingleReport(reportId)
    }
    suspend fun getAllReportsWithQuery(longitude: String, latitude: String): Response<ReportResponse> {
        return apiService.getAllReportsWithQuery(longitude, latitude)
    }
}

