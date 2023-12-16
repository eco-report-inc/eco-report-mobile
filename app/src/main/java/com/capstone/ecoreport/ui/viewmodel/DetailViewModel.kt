package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.DummyRepository
import com.capstone.ecoreport.model.Dummy
import com.capstone.ecoreport.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DummyRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Dummy>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Dummy>> get() = _uiState

    fun getDummyById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getDummyById(id))
    }
}
//Bisa menggunakan ini DetailViewModel atau langsung dari ReportViewModel
/*
package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ReportRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<ReportData>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<ReportData>> get() = _uiState

    // Mengganti fungsi untuk mendapatkan data berdasarkan ID
    fun getReportById(reportId: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        repository.getSingleReport(reportId)
            .let { response ->
                if (response != null) {
                    _uiState.value = UiState.Success(response)
                } else {
                    _uiState.value = UiState.Error("Failed to retrieve report details")
                }
            }
    }
}

*/