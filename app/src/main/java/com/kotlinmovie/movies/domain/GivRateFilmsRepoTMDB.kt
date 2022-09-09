package com.kotlinmovie.movies.domain

import androidx.annotation.WorkerThread
import com.kotlinmovie.movies.data.movie.FilmCardEntity
import kotlin.jvm.Throws
import kotlin.reflect.KFunction1


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

    fun getMovieInfo(
        id: Int,
        onSuccess: KFunction1<FilmCardEntity, Unit>,
        onError: (Throwable) -> Unit
    )
}
