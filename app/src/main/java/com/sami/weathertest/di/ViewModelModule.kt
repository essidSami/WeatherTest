package com.sami.weathertest.di

import com.sami.weathertest.data.repository.RepositoryImpl
import com.sami.weathertest.data.repository.RepositoryInterface
import com.sami.weathertest.data.source.remote.abstraction.RetrofitRemoteDataSource
import com.sami.weathertest.data.source.remote.implementation.Network
import com.sami.weathertest.data.source.remote.implementation.RetrofitRemoteDataSourceImpl
import com.sami.weathertest.ui.listing.AllWeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { AllWeatherViewModel(get()) }
}

val repositoryModule: Module = module {
    single { RepositoryImpl(get()) as RepositoryInterface }
}

val dataSourceModule: Module = module {
    single { RetrofitRemoteDataSourceImpl(get()) as RetrofitRemoteDataSource }
}

val networkModule: Module = module {
    single { Network(androidApplication()).weatherApi }
}