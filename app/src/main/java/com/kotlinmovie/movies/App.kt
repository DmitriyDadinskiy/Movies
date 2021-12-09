package com.kotlinmovie.movies

import android.app.Application
import android.content.Context
import com.kotlinmovie.movies.data.WebGivFilmsTopRatedImpl
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB

class App : Application() {
    val givRateFilmsRepoTMDB: GivRateFilmsRepoTMDB by lazy {
        WebGivFilmsTopRatedImpl(null)
    }


}

val Context.app
    get() = applicationContext as App
