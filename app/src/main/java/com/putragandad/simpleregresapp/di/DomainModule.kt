package com.putragandad.simpleregresapp.di

import com.putragandad.simpleregresapp.domain.usecase.CheckPalindromeUseCase
import org.koin.dsl.module

object DomainModule {
    val useCaseModule = module {
        factory { CheckPalindromeUseCase() }
    }
}