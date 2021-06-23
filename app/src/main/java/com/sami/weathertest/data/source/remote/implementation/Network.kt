package com.sami.weathertest.data.source.remote.implementation

import android.content.Context
import com.google.gson.GsonBuilder
import com.sami.weathertest.data.source.remote.abstraction.RetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class Network constructor(
    context: Context
){

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    private val gson = GsonBuilder().serializeNulls().create()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val weatherApi: RetrofitApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RetrofitApi::class.java)
}