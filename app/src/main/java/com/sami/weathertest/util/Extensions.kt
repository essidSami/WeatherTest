package com.sami.weathertest.util

import androidx.lifecycle.MutableLiveData
import com.sami.weathertest.data.model.Resource
import com.sami.weathertest.data.model.ResourceStatus
import retrofit2.HttpException

fun MutableLiveData<Resource>.setLoading() {

    postValue(Resource(status = ResourceStatus.LOADING, data = null))
}

fun MutableLiveData<Resource>.setSuccess(data: Any?) {

    postValue(Resource(status = ResourceStatus.SUCCESS, data = data))
}

fun MutableLiveData<Resource>.setError(error: Throwable) {

    if (error is HttpException) postValue(Resource(status = ResourceStatus.ERROR, data = error.code()))
    else postValue(Resource(status = ResourceStatus.ERROR, data = null))
}