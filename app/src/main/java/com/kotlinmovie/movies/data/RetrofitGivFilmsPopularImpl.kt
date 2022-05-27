package com.kotlinmovie.movies.data

import com.kotlinmovie.movies.data.retrofit.TMDBRepoApi
import com.kotlinmovie.movies.domain.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

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

    override fun getSearchMoves(
        page: Int,
        query: String,
        includeAdult: Boolean,
        onSuccess: (MutableList<FilmListSearch>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.loadReposSearchFilms(page = page, query = query, includeAdult = includeAdult)
            .enqueue(object : Callback<SearchResult>{
            override fun onResponse(call: Call<SearchResult>,
                                    response: Response<SearchResult>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        onSuccess.invoke(responseBody.results)
                    }else{
                        onError.invoke(Throwable())
                    }
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                onError.invoke(Throwable())
            }

        })
    }


}
