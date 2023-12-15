package com.capstone.ecoreport.data.Report

import com.capstone.ecoreport.data.api.ApiService
import com.capstone.ecoreport.data.models.ReportResponse
import retrofit2.Response

class ReportManager(private val apiService: ApiService) {
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

