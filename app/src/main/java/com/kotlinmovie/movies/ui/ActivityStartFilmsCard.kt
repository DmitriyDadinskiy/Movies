package com.kotlinmovie.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ActivityMainBinding
import com.kotlinmovie.movies.databinding.ActivityStartFilmsCardBinding
import com.squareup.picasso.Picasso

class ActivityStartFilmsCard : AppCompatActivity() {

    private lateinit var binding: ActivityStartFilmsCardBinding

    companion object {
        const val POSTER_PATCH = "poster_patch"
        const val TITLE = "title"
        const val VOTE_AVERAGE = "voteAverage"
        const val RELEASE_DATE = "releaseDate"
        const val OVERVIEW = "overview"

    }

    private lateinit var filmsCardNameTextView: TextView
    private lateinit var filmCardBannerImageView: ImageView
    private lateinit var filmCardRatingTextView: TextView
    private lateinit var filmCardDateTextView: TextView
    private lateinit var filmCardDescriptionTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartFilmsCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        initView()
        putFilmDetails()
    }


    private fun initView() {
        filmsCardNameTextView = binding.filmCardNameTextView
        filmCardBannerImageView = binding.filmCardBannerImageView
        filmCardRatingTextView = binding.filmCardRatingTextView
        filmCardDateTextView = binding.filmCardDateTextView
        filmCardDescriptionTextView = binding.filmCardDescriptionTextView

    }

    private fun putFilmDetails() {
        val putInfoFilms = intent.extras

        if (putInfoFilms != null) {
            filmsCardNameTextView.text = putInfoFilms.getString(TITLE,"")
            filmCardRatingTextView.text = putInfoFilms.getDouble(VOTE_AVERAGE).toString()
            filmCardDateTextView.text = putInfoFilms.getString(RELEASE_DATE,"")
            filmCardDescriptionTextView.text = putInfoFilms.getString(OVERVIEW,"")

                Picasso.get().load(IMAGES_PATH+ putInfoFilms.getString(POSTER_PATCH))
                    .into(filmCardBannerImageView)

        }

    }
}
