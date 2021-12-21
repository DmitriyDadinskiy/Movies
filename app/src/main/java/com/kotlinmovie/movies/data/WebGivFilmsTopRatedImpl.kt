package com.kotlinmovie.movies.data


import android.util.Log
import com.google.gson.Gson
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WebGivFilmsTopRatedImpl(private val loadException: LoadFilmsError?) :
    GivRateFilmsRepoTMDB {

    override fun getRateFilms(categoryFilms: String): MutableList<FilmsListWatchingNow> {
        val resultArr = emptyList<FilmsListWatchingNow>().toMutableList()
        var connection: HttpURLConnection? = null
        //    val topRatedImpl =
        "https://api.themoviedb.org/3/movie/550?api_key=feec2f259352fcecc420544fb3ba88de"
        try {
            //        val url = URL(topRatedImpl)
//       val moveId = arrayOf(2,19404,278,238, 724089,424, 696374,240, 255709,372058, 129) //гарантированные  ИД фильмов
            repeat(3) {
                val filmsNumber = (2..409000).random()
                connection = getURL(filmsNumber).openConnection() as HttpURLConnection

                connection!!.requestMethod = "GET"
                val bufferedReader = BufferedReader(InputStreamReader(connection!!.inputStream))
                val resultWeb = bufferedReader.readLine().toString()

                val repoTMDB = gson.fromJson(
                    resultWeb,
                    FilmsListWatchingNow::class.java
                )
                arrayOf(repoTMDB).forEach {
                    resultArr.add(it)
                }
            }
        } catch (e: Exception) {
            Log.e("ОШИБКА!!!", "Поймал ошибку при получении данных из интернета", e)
            e.printStackTrace()
            loadException?.loadFilmsError()


        } finally {
            connection?.disconnect()
        }

        return resultArr
    }


    private val gson by lazy { Gson() }
    private fun getURL(filmsNumber: Int) =
        URL("https://api.themoviedb.org/3/movie/$filmsNumber?api_key=feec2f259352fcecc420544fb3ba88de&language=ru")
}

interface LoadFilmsError {
    fun loadFilmsError()
}
