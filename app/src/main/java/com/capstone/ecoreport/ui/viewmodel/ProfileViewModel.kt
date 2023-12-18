package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.user.ProfilePhotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProfileViewModel(private val repository: ProfilePhotoRepository) : ViewModel() {

    private val _uploadState: MutableStateFlow<UploadState> = MutableStateFlow(UploadState.Idle)

    val uploadState: StateFlow<UploadState> get() = _uploadState

    sealed class UploadState {
        object Idle : UploadState()
        object Loading : UploadState()
        data class Success(val message: String) : UploadState()
        data class Error(val error: String) : UploadState()
    }

    // Fungsi untuk mengupload foto profil
    fun uploadProfilePhoto(photo: MultipartBody.Part) = viewModelScope.launch {
        _uploadState.value = UploadState.Loading
        repository.uploadProfilePhoto(photo)
            .let { response ->
                if (response != null) {
                    _uploadState.value = UploadState.Success(response.message)
                } else {
                    _uploadState.value = UploadState.Error("Failed to upload profile photo")
                }
            }
    }
}
