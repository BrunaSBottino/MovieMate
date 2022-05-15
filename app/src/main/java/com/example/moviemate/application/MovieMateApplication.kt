package com.example.moviemate.application

import android.app.Application
import com.example.moviemate.di.networkModule
import com.example.moviemate.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieMateApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieMateApplication)
            modules(viewModelsModule, networkModule)
        }
    }

}