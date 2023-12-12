package com.capstone.ecoreport.dl

import com.capstone.ecoreport.data.DummyRepository

object Injection {
    fun provideRepository(): DummyRepository {
        return DummyRepository.getInstance()
    }
}