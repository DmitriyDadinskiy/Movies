package com.kotlinmovie.movies.domain

import com.google.gson.annotations.SerializedName

data class FilmsListRecommendation(
    @SerializedName("poster_path")
    val posterPath: String, //постер фильма
    @SerializedName("title")
    val title: String, //название фильма
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double, //рейтинг фильма
    val overview: String, // описание
    val id: Int, // ИД фильма

)

data class MovieResultTopRated(
    val results: List<FilmsListRecommendation>
)
