package com.capstone.ecoreport.data.Report

import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.data.models.ReportResponse
import retrofit2.Response

class ReportRepository(private val reportManager: ReportManager) {

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
    suspend fun getAllReportsWithQuery(longitude: String, latitude: String): List<ReportData>? {
        val response = reportManager.getAllReportsWithQuery(longitude, latitude)
        return if (response.isSuccessful) {
            response.body()?.reportData
        } else {
            null
        }
    }
}
