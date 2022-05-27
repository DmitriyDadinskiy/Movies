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
}