package com.sami.weathertest.data.source.remote.abstraction

import com.sami.weathertest.data.model.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    //get weather by city name
    @GET("weather")
    fun getWeatherByCity(
       @Query("q") cityName: String,
       @Query("appid") appKey: String
    ): Single<CityWeatherResponse>
}