package com.capstone.ecoreport.data.user


import com.capstone.ecoreport.data.models.UploadPhotoResponse
import okhttp3.MultipartBody
class ProfilePhotoRepository(private val profilePhotoManager: ProfilePhotoManager) {

    suspend fun uploadProfilePhoto(photo: MultipartBody.Part): UploadPhotoResponse? {
        val response = profilePhotoManager.uploadProfilePhoto(photo)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
