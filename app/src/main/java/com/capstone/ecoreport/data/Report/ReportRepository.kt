package com.capstone.ecoreport.data.Report

import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.data.models.ReportResponse
import okhttp3.MultipartBody

class ReportRepository(private val reportManager: ReportManager) {

    suspend fun postAddReport(
        placeName: String,
        latitude: String,
        longitude: String,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part
    ): ReportResponse? {
        val response = reportManager.postAddReport(placeName, latitude, longitude, image1, image2)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getAllReports(): List<ReportData>? {
        val response = reportManager.getAllReports()
        return if (response.isSuccessful) {
            response.body()?.reportData
        } else {
            null
        }
    }
    suspend fun getSingleReport(reportId: String): ReportData? {
        val response = reportManager.getSingleReport(reportId)
        return if (response.isSuccessful) {
            response.body()?.reportData?.firstOrNull()
        } else {
            null
        }
    }
}
