package com.kotlinmovie.movies

import android.app.Application
import android.content.Context
import com.kotlinmovie.movies.data.RetrofitGivFilmsPopularImpl
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import com.kotlinmovie.movies.room.model.NoteRepo
import com.kotlinmovie.movies.room.impl.RoomNoteRepoImpl

class App : Application() {
    val givRateFilmsRepoTMDB: GivRateFilmsRepoTMDB by lazy {
        RetrofitGivFilmsPopularImpl() }

    val noteFilm: NoteRepo by lazy { RoomNoteRepoImpl(this) }
}


val Context.app
    get() = applicationContext as App
