package com.capstone.ecoreport.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.DummyRepository
import com.capstone.ecoreport.model.Dummy
import com.capstone.ecoreport.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DummyRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Dummy>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Dummy>>> get() = _uiState

    private val _query = mutableStateOf("")

    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchDummy(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}