package com.sami.weathertest.data.repository

import com.sami.weathertest.data.model.CityWeatherResponse
import com.sami.weathertest.data.source.remote.abstraction.RetrofitRemoteDataSource
import io.reactivex.Single

class RepositoryImpl constructor(
    private val remoteDataSource: RetrofitRemoteDataSource
) : RepositoryInterface {

    //get weather by city name
    override fun getWeatherByCity(cityName: String, appKey: String): Single<CityWeatherResponse> =
        remoteDataSource.getWeatherByCity(cityName, appKey)
}