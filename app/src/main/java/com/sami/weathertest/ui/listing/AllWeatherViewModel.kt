package com.sami.weathertest.ui.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sami.weathertest.BuildConfig
import com.sami.weathertest.data.model.CityWeatherResponse
import com.sami.weathertest.data.model.Resource
import com.sami.weathertest.data.repository.RepositoryInterface
import com.sami.weathertest.util.setError
import com.sami.weathertest.util.setLoading
import com.sami.weathertest.util.setSuccess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AllWeatherViewModel constructor(private val repository: RepositoryInterface): ViewModel(){

    //disposable
    private val disposable = CompositeDisposable()
    var progressStatus = MutableLiveData<Int>(0)
    var waitingMsg = MutableLiveData<Int>(1)
    val weatherResponseList = MutableLiveData<MutableList<CityWeatherResponse>>()
    private val cityWeatherLiveData = MutableLiveData<Resource>()

    /**
     * This method allows get data weather by city
     */
    fun getCityWeather(city: String) {

        disposable.add(repository.getWeatherByCity(city, BuildConfig.API_KEY)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                cityWeatherLiveData.setLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cityWeatherLiveData.setSuccess(it)
            }, {
                cityWeatherLiveData.setError(it)
            })
        )
    }

    fun getCityWeatherLiveData(): MutableLiveData<Resource> = cityWeatherLiveData

    fun clearResourceLiveData(resourceLiveData: MutableLiveData<Resource>) {
        resourceLiveData.value = null
    }

    /**
     * This method
     * dispose disposable
     * on cleared
     */
    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}