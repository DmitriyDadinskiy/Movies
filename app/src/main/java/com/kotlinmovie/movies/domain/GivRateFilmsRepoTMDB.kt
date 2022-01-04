package com.kotlinmovie.movies.domain

import androidx.annotation.WorkerThread
import kotlin.jvm.Throws


interface GivRateFilmsRepoTMDB {
    @WorkerThread
    @Throws(Throwable::class)
    fun getRateFilms(categoryFilms: List<FilmsListWatchingNow>,
                     onError: (Throwable) -> Unit): MutableList<FilmsListWatchingNow>

}