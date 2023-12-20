package com.capstone.ecoreport

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

class EcoReportApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@EcoReportApplication)
            modules(defaultModule)
        }
    }
}