package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.user.ProfilePhotoRepository
import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val reportRepository: ReportRepository,
    private val profilePhotoRepository: ProfilePhotoRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<ReportData>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ReportData>> get() = _uiState

    // Mengganti fungsi untuk mendapatkan data berdasarkan ID
    fun getReportById(reportId: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        reportRepository.getSingleReport(reportId)
            .let { response ->
                if (response != null) {
                    _uiState.value = UiState.Success(response)
                } else {
                    _uiState.value = UiState.Error("Failed to retrieve report details")
                }
            }
    }
}
