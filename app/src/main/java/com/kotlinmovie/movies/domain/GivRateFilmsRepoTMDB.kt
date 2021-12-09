package com.kotlinmovie.movies.domain

import androidx.annotation.WorkerThread
import com.kotlinmovie.movies.data.FilmsListWatchingNow


interface GivRateFilmsRepoTMDB {
    @WorkerThread
    fun getRateFilms(categoryFilms: String): MutableList<FilmsListWatchingNow>

}