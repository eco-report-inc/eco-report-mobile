package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.ReportResult
import com.capstone.ecoreport.data.models.ReportData
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody

class ReportViewModel(private val reportRepository: ReportRepository) : ViewModel() {

    fun postAddReport(
        placeName: String,
        latitude: String,
        longitude: String,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part
    ): LiveData<ReportResult> = liveData(Dispatchers.IO) {
        emit(ReportResult.Loading)
        try {
            val response = reportRepository.postAddReport(
                placeName,
                latitude,
                longitude,
                image1,
                image2
            )

            if(response != null) {
                emit(ReportResult.Success(response))
            } else {
                emit(ReportResult.Error("Failed to add report"))
            }
        } catch (e: Exception) {
            emit(ReportResult.Error(e.message ?: "Unknown error occurred"))
        }
    }

    fun getAllReports(): LiveData<List<ReportData>> = liveData(Dispatchers.IO) {
        emit(emptyList())
        try {
            val response = reportRepository.getAllReports()
            response?.let { emit(it) }
        } catch(e: Exception) {
            emit(emptyList())
        }
    }

    fun getSingleReport(reportId: String): LiveData<ReportData?> = liveData(Dispatchers.IO) {
        emit(null)
        try {
            val response = reportRepository.getSingleReport(reportId)
            response?.let { emit(it) }
        } catch(e: Exception) {
            emit(null)
        }
    }

}