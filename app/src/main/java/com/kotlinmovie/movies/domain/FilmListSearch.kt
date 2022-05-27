package com.kotlinmovie.movies.domain

import com.google.gson.annotations.SerializedName

data class FilmListSearch(

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

data class SearchResult(
    val results: MutableList<FilmListSearch>
)

