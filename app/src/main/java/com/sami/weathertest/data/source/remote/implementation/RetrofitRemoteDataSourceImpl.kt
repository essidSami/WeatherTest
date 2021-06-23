package com.sami.weathertest.data.source.remote.implementation

import com.sami.weathertest.data.model.CityWeatherResponse
import com.sami.weathertest.data.source.remote.abstraction.RetrofitApi
import com.sami.weathertest.data.source.remote.abstraction.RetrofitRemoteDataSource
import io.reactivex.Single

class RetrofitRemoteDataSourceImpl constructor(
    private val api: RetrofitApi
) : RetrofitRemoteDataSource {

    override fun getWeatherByCity(cityName: String, appKey: String): Single<CityWeatherResponse> =
        api.getWeatherByCity(cityName, appKey)
}