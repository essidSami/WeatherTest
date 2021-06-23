package com.sami.weathertest.data.source.remote.abstraction

import com.sami.weathertest.data.model.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.Query

interface RetrofitRemoteDataSource {

    //get weather by city name
    fun getWeatherByCity(
        cityName: String, appKey: String
    ): Single<CityWeatherResponse>
}