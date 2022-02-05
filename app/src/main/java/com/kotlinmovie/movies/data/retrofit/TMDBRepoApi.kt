package com.kotlinmovie.movies.data.retrofit

import com.kotlinmovie.movies.domain.MovieResult
import com.kotlinmovie.movies.domain.MovieResultTopRated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query




interface TMDBRepoApi {
    @GET("/3/movie/popular?api_key=feec2f259352fcecc420544fb3ba88de&language=ru&page=1")
    fun loadReposFilmsListPopular(): Call<MovieResult>

    @GET("/3/movie/top_rated?api_key=feec2f259352fcecc420544fb3ba88de&language=ru&page=1")
    fun loadReposFilmsListTopRated(): Call<MovieResultTopRated>

}
