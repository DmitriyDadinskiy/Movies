package com.kotlinmovie.movies.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlinmovie.movies.BuildConfig
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.app
import com.kotlinmovie.movies.data.movie.FilmCardEntity
import com.kotlinmovie.movies.databinding.ActivityStartFilmsCardBinding
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import com.kotlinmovie.movies.room.model.NoteFilm
import com.kotlinmovie.movies.room.model.NoteRepo
import com.squareup.picasso.Picasso
import com.yandex.mapkit.MapKitFactory

class ActivityStartFilmsCard : AppCompatActivity() {

    private lateinit var binding: ActivityStartFilmsCardBinding
    private val noteRepo: NoteRepo by lazy { app.noteFilm }
    private val givRateFilmsTMDB: GivRateFilmsRepoTMDB by lazy { app.givRateFilmsRepoTMDB }

    companion object {
        const val ID = "id_films"
    }


    private var idFilms: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartFilmsCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        putFilmDetails()
        actionButtons()
        viewLocationCountry()

    }

    private fun actionButtons() {
        binding.backImageButton.setOnClickListener {
            finish()
        }
        binding.noteStartImageButton.setOnClickListener {
            if (binding.noteLinearLayout.visibility != View.VISIBLE) {
                getNoteFilm()
                binding.noteLinearLayout.visibility = View.VISIBLE
            } else {
                binding.noteLinearLayout.visibility = View.GONE
                addNoteFilm()
            }
        }
        binding.clearTextButton.setOnClickListener {

            Thread {
                val searchNoteFilm = noteRepo.getNotesListFilm(idFilms)
                noteRepo.delete(searchNoteFilm)
                runOnUiThread { binding.textNoteEditText.setText("") }
            }.start()

            Toast.makeText(
                applicationContext, getString(R.string.delete_note_film),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addNoteFilm() {
        val textNoteEditText = binding.textNoteEditText
        val textNote = textNoteEditText.text.toString()
        val note = NoteFilm(idFilms, textNote)
        Thread {
            val checkIdFilm = noteRepo.exists(idFilms)
            val searchNoteFilm = noteRepo.getNotesIdFilm(idFilms)
            if (!checkIdFilm) {
                if (textNote.trim().isNotEmpty()) {
                    noteRepo.add(note)
                }
            } else {
                if (textNote != searchNoteFilm.note) {
                    noteRepo.updateNoteFilm(note)
                }
            }

        }.start()
    }

    private fun getNoteFilm() {


        Thread {
            val checkIdFilm = noteRepo.exists(idFilms)
            val searchNoteFilm = noteRepo.getNotesIdFilm(idFilms)
            if (checkIdFilm) {
                val texNoteRoomDb = searchNoteFilm.note
                runOnUiThread {
                    binding.textNoteEditText.setText(texNoteRoomDb)
                }
            }
        }.start()
    }


    private fun putFilmDetails() {
        val putInfoFilms = intent.extras
        if (putInfoFilms != null) {
            idFilms = putInfoFilms.getInt(ID, 0)
        }
        getMovieInfo()


    }


    private fun getMovieInfo() {
        givRateFilmsTMDB.getMovieInfo(
            id = idFilms,
            onSuccess = ::gotMovie,
        ) {
            Toast.makeText(
                applicationContext, "нет подключения к ${it.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun gotMovie(result: FilmCardEntity) {
        val nameGenres = result.genres[0].name
        val nameCountry = result.productionCountries[0].name
        with(binding) {
            filmCardNameTextView.text = result.title
            filmCardRatingTextView.text = result.voteAverage.toString()
            filmCardDateTextView.text = result.releaseDate
            filmCardDescriptionTextView.text = result.overview
            filmCardGenreTextView.text = nameGenres
            filmCardBudgetDollarTextView.text = result.budget.toString()
            filmCardFeesDollarTextView.text = result.revenue.toString()
            filmCardVoteCountTextView.text = result.voteCount.toString()
            Picasso.get().load(IMAGES_PATH + result.posterPath)
                .into(filmCardBannerImageView)
            filmCardCountryPasteTextView.text = nameCountry
        }
    }
    private fun viewLocationCountry(){
        binding.filmCardCountryPasteTextView.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_films_card, MapYandexFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
    }

}
