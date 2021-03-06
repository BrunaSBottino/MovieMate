package com.example.moviemate.di

import com.example.moviemate.network.Repository
import com.example.moviemate.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel{ MainViewModel(get()) }

}

val networkModule = module {

    factory { Repository() }

}