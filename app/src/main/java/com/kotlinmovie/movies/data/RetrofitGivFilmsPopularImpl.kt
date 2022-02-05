package com.kotlinmovie.movies.data

import com.kotlinmovie.movies.data.retrofit.TMDBRepoApi
import com.kotlinmovie.movies.domain.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/"

class RetrofitGivFilmsPopularImpl : GivRateFilmsRepoTMDB {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    private var service: TMDBRepoApi = retrofit.create(TMDBRepoApi::class.java)

    override fun getPopularFilms(
        onSuccess: (result: List<FilmsListWatchingNow>) -> Unit,
        onError: (Throwable) -> Unit,
    ) {

        service.loadReposFilmsListPopular().enqueue(object : Callback<MovieResult> {
            override fun onResponse(
                call: Call<MovieResult>, response: Response<MovieResult>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.results)
                    } else {
                        onError.invoke(Throwable())
                    }
                }
            }

            override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                onError.invoke(Throwable())
            }
        })
    }

    override fun getTopRatedFilms(
        onSuccess: (result: List<FilmsListRecommendation>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.loadReposFilmsListTopRated().enqueue(object : Callback<MovieResultTopRated> {
            override fun onResponse(
                call: Call<MovieResultTopRated>,
                response: Response<MovieResultTopRated>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        onSuccess.invoke(responseBody.results)
                    } else {
                        onError.invoke(Throwable())
                    }
                }
            }

            override fun onFailure(call: Call<MovieResultTopRated>, t: Throwable) {
                onError.invoke(Throwable())
            }

        })
    }
}
