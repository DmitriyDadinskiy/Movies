package com.kotlinmovie.movies.data


data class FilmsListWatchingNow(


    val title: String, // название ФИЛЬМА
    val vote_average: Double, //РЕЙТИНГ
    val release_date: String, //   ПРОВЕРИТЬ возможно тип число или DATA
 //   val id: Int, // ИД фильма
//    val genresName: String, //жанр, но в АПИ Список, как отобразить??
//    val runtime: Int,
//    val budget: Int,
//    val revenue: Int, // сборы
//    val vote_count: Int, //кол-во оценок
//    val overview: String, // описание
//    val poster_path: Int //постер
)

