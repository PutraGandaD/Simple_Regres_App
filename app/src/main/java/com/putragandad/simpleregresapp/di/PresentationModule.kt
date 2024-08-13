package com.putragandad.simpleregresapp.di

import com.putragandad.simpleregresapp.presentation.viewmodel.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    val viewModelModule = module {
        viewModel { SharedViewModel(get()) }
    }
}