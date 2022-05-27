package com.kotlinmovie.movies.domain

import androidx.annotation.WorkerThread
import kotlin.jvm.Throws


interface GivRateFilmsRepoTMDB {
    @WorkerThread
    @Throws(Throwable::class)
    fun getPopularFilms(
        onSuccess: (List<FilmsListWatchingNow>) -> Unit,
        onError: (Throwable) -> Unit,
    ) {

    }
    @WorkerThread
    @Throws(Throwable::class)
    fun getTopRatedFilms(
        onSuccess: (List<FilmsListRecommendation>) -> Unit,
        onError: (Throwable) -> Unit,
    ) {

    }
    @WorkerThread
    @Throws(Throwable::class)
    fun getSearchMoves(
        page: Int,
        query: String,
        includeAdult: Boolean,
        onSuccess: (MutableList<FilmListSearch>) -> Unit,
        onError: (Throwable) -> Unit,)

}
