package com.putragandad.simpleregresapp

import android.app.Application
import com.putragandad.simpleregresapp.di.DataModule.dataSourceModule
import com.putragandad.simpleregresapp.di.DataModule.repositoryModule
import com.putragandad.simpleregresapp.di.DomainModule.useCaseModule
import com.putragandad.simpleregresapp.di.NetworkModule.networkModule
import com.putragandad.simpleregresapp.di.PresentationModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(
                dataSourceModule, // data
                networkModule, // data
                repositoryModule, // data
                useCaseModule, // domain
                viewModelModule // presentation
            ))
        }
    }
}