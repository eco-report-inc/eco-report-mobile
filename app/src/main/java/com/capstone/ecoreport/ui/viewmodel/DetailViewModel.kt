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