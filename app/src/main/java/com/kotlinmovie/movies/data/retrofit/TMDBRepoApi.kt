package com.kotlinmovie.movies.data.retrofit

import com.kotlinmovie.movies.data.movie.FilmCardEntity
import com.kotlinmovie.movies.domain.MovieResult
import com.kotlinmovie.movies.domain.MovieResultTopRated
import com.kotlinmovie.movies.domain.SearchResult
import com.kotlinmovie.movies.ui.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query




interface TMDBRepoApi {
    @GET("movie/popular?api_key=feec2f259352fcecc420544fb3ba88de&language=ru&page=1&region=RU")
    fun loadReposFilmsListPopular(): Call<MovieResult>

    @GET("movie/top_rated?api_key=feec2f259352fcecc420544fb3ba88de&language=ru&page=1&region=RU")
    fun loadReposFilmsListTopRated(): Call<MovieResultTopRated>

    @GET("search/movie")
    fun loadReposSearchFilms(
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "ru",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("region") region: String = "RU"
    ): Call<SearchResult>

    @GET("movie/{id}")
    fun loadReposMoviesInfo(
        @Path("id") id: Int,
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "ru"
    ): Call<FilmCardEntity>
}

