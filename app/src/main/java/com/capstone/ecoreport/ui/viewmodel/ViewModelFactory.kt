package com.capstone.ecoreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.user.ProfilePhotoRepository

class ViewModelFactory(
    private val reportRepository: ReportRepository,
    private val profilePhotoRepository: ProfilePhotoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(reportRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(profilePhotoRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(reportRepository, profilePhotoRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
