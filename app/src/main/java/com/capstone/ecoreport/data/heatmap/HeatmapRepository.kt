package com.capstone.ecoreport.data.heatmap

import com.capstone.ecoreport.data.models.ReportData
import com.google.android.gms.maps.GoogleMap

class HeatmapRepository(private val heatmapManager: HeatmapManager) {

    suspend fun getAllReportsWithQuery(longitude: String, latitude: String): List<ReportData>? {
        val response = heatmapManager.getAllReportsWithQuery(longitude, latitude)
        return if (response.isSuccessful) {
            response.body()?.reportData
        } else {
            null
        }
    }
    suspend fun createHeatmap(googleMap: GoogleMap, reportDataList: List<ReportData>) {
        heatmapManager.createHeatmap(googleMap, reportDataList)
    }
}