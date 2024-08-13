package com.putragandad.simpleregresapp.di

import com.putragandad.simpleregresapp.data.implementation.UserRepositoryImpl
import com.putragandad.simpleregresapp.data.source.remote.RemoteDataSource
import com.putragandad.simpleregresapp.domain.repository.IUserRepository
import org.koin.dsl.module

object DataModule {
    val dataSourceModule = module {
        single { RemoteDataSource(get()) }
    }

    val repositoryModule = module {
        factory<IUserRepository> { UserRepositoryImpl(get()) }
    }
}