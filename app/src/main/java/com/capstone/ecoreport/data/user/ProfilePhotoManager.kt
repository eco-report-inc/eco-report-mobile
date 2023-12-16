package com.capstone.ecoreport.data.user

import com.capstone.ecoreport.data.api.ApiService
import com.capstone.ecoreport.data.models.UploadPhotoResponse
import okhttp3.MultipartBody
import retrofit2.Response

class ProfilePhotoManager(private val apiService: ApiService) {

    suspend fun uploadProfilePhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> {
        return apiService.uploadProfilePhoto(photo)
    }
}
