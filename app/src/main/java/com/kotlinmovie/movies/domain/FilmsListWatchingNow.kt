package com.kotlinmovie.movies.domain

import com.google.gson.annotations.SerializedName


data class FilmsListWatchingNow(
    @SerializedName("title")
    val title: String, // название ФИЛЬМА
    @SerializedName("vote_average")
    val voteAverage: Double, //РЕЙТИНГ
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String, //постер
    val overview: String, // описание
    val id: Int, // ИД фильма
)

data class MovieResult(
    val results: List<FilmsListWatchingNow>
)


//   val id: Int, // ИД фильма
//    val genresName: String, //жанр, но в АПИ Список, как отобразить??
//    val runtime: Int,
//    val budget: Int,
//    val revenue: Int, // сборы
//    val vote_count: Int, //кол-во оценок
//    val overview: String, // описание
//    val poster_path: Int //постер



