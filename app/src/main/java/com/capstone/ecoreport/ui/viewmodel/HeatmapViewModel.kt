package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.ecoreport.data.heatmap.HeatmapRepository
import com.capstone.ecoreport.data.models.ReportData
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HeatmapViewModel(private val heatmapRepository: HeatmapRepository) : ViewModel() {

    fun getAllReportsWithQuery(
        longitude: String,
        latitude: String
    ): LiveData<List<ReportData>> {
        return liveData(Dispatchers.IO) {
            emit(emptyList())
            try {
                val response = heatmapRepository.getAllReportsWithQuery(longitude, latitude)
                response?.let { emit(it) }
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }
    suspend fun createHeatmap(googleMap: GoogleMap, reportDataList: List<ReportData>) {
        withContext(Dispatchers.Main) {
            heatmapRepository.createHeatmap(googleMap, reportDataList)
        }
    }
}