package com.kotlinmovie.movies.data


import android.util.Log
import com.google.gson.Gson
import com.kotlinmovie.movies.domain.FilmsListWatchingNow
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WebGivFilmsTopRatedImpl : GivRateFilmsRepoTMDB {


    override fun getRateFilms(
        categoryFilms: List<FilmsListWatchingNow>,
        onError: (Throwable) -> Unit
    ): MutableList<FilmsListWatchingNow> {

        val resultArr = emptyList<FilmsListWatchingNow>().toMutableList()
        var connection: HttpURLConnection? = null
        val gson by lazy { Gson() }
        fun getURL(filmsNumber: Int) =
            URL("https://api.themoviedb.org/3/movie/$filmsNumber?api_key=feec2f259352fcecc420544fb3ba88de&language=ru")
        //    val topRatedImpl = "https://api.themoviedb.org/3/movie/550?api_key=feec2f259352fcecc420544fb3ba88de" //тестовый адресс
        try {
            //        val url = URL(topRatedImpl)
            repeat(3) {
                val filmsNumber = (2..409000).random()
                connection = getURL(filmsNumber).openConnection() as HttpURLConnection

                connection!!.requestMethod = "GET"
                val bufferedReader = BufferedReader(InputStreamReader(connection!!.inputStream))
                val resultWeb = bufferedReader.readLine().toString()

                val repoTMDB = gson.fromJson(resultWeb, FilmsListWatchingNow::class.java)

                arrayOf(repoTMDB).forEach {
                    resultArr.add(it)
                }
            }
        } catch (e: Throwable) {
            onError(e)
            Log.e("ОШИБКА!!!", "Поймал ошибку при получении данных из интернета", e)


        } finally {
            connection?.disconnect()
        }

        return resultArr
    }


}


