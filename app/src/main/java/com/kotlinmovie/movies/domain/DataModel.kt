package com.kotlinmovie.movies.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val sendingSearchRequest: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val positionAdultSwitch: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val latitudeMyPosition: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val longitudeMyPosition: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
}