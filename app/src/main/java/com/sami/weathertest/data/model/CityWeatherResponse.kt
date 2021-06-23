package com.sami.weathertest.data.model

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse (
    @SerializedName("weather")
    val weather: List<Weather>?,
    @SerializedName("main")
    val temperature: Temperature?,
    @SerializedName("temp")
    val wind: Wind?,
    @SerializedName("name")
    var name: String,
    @SerializedName("cod")
    val cod: Int
        )