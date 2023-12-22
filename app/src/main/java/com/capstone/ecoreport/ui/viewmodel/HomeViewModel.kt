package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ReportRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<ReportData>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<ReportData>>> get() = _uiState
    fun getAllReports() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAllReports()?.let {
                _uiState.value = UiState.Success(it)
            } ?: run {
                _uiState.value = UiState.Error("Failed to fetch reports")
            }
        }
    }
}