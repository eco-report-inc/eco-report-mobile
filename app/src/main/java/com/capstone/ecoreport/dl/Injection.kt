package com.capstone.ecoreport.dl

import com.capstone.ecoreport.data.Report.ReportManager
import com.capstone.ecoreport.data.Report.ReportRepository
import com.capstone.ecoreport.data.api.ApiConfig

object Injection {
    private val reportManager = ReportManager(ApiConfig.getApiService())
    private val reportRepository = ReportRepository(reportManager)

    fun provideRepository(): ReportRepository {
        return reportRepository
    }
}

