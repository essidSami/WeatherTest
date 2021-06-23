package com.sami.weathertest.data.repository

import com.sami.weathertest.data.model.CityWeatherResponse
import io.reactivex.Single

interface RepositoryInterface {

    //get weather by city name
    fun getWeatherByCity(
        cityName: String, appKey: String
    ): Single<CityWeatherResponse>
}