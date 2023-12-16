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
//Bisa menggunakan ini HomeViewModel atau langsung dari ReportViewModel
/*// ViewModel untuk tampilan utama (Home)
class HomeViewModel(private val repository: ReportRepository) : ViewModel() {

    // State untuk UI, menggunakan Flow untuk pemantauan perubahan
    private val _uiState: MutableStateFlow<UiState<List<ReportData>>> =
        MutableStateFlow(UiState.Loading)

    // Exposed StateFlow agar dapat diakses oleh komponen lain
    val uiState: StateFlow<UiState<List<ReportData>>> get() = _uiState

    // Pencarian berdasarkan query
    private val _query = mutableStateOf("")

    // Exposed State untuk query agar dapat diakses oleh komponen lain
    val query: State<String> get() = _query

    // Fungsi untuk melakukan pencarian
    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.getAllReportsWithQuery(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}
*/