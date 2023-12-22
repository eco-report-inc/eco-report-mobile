package com.capstone.ecoreport.dl

import com.capstone.ecoreport.data.Report.ReportManager
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.api.ApiConfig
import com.capstone.ecoreport.data.user.ProfilePhotoManager
import com.capstone.ecoreport.data.user.ProfilePhotoRepository

object Injection {
    private val reportManager = ReportManager(ApiConfig.getApiService())
    private val profilePhotoManager = ProfilePhotoManager(ApiConfig.getApiService()) // Assuming you have a ProfilePhotoManager class
    private val reportRepository = ReportRepository(reportManager)
    private val profilePhotoRepository = ProfilePhotoRepository(profilePhotoManager)

    fun provideRepository(): ReportRepository {
        return reportRepository
    }

    fun provideProfilePhotoRepository(): ProfilePhotoRepository {
        return profilePhotoRepository
    }
}


