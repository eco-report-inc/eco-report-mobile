package com.capstone.ecoreport

import com.capstone.ecoreport.ui.viewmodel.TrashDetectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TrashDetectionViewModel(get()) }
}