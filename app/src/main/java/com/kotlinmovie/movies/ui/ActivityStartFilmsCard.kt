package com.kotlinmovie.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.app
import com.kotlinmovie.movies.databinding.ActivityStartFilmsCardBinding
import com.kotlinmovie.movies.room.model.NoteFilm
import com.kotlinmovie.movies.room.model.NoteRepo
import com.squareup.picasso.Picasso

class ActivityStartFilmsCard : AppCompatActivity() {

    private lateinit var binding: ActivityStartFilmsCardBinding
    private val noteRepo: NoteRepo by lazy { app.noteFilm }

    companion object {
        const val POSTER_PATCH = "poster_patch"
        const val TITLE = "title"
        const val VOTE_AVERAGE = "voteAverage"
        const val RELEASE_DATE = "releaseDate"
        const val OVERVIEW = "overview"
        const val ID = "id_films"
    }


    private lateinit var filmCardBannerImageView: ImageView

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
    }

    private fun actionButtons() {
        binding.backImageButton.setOnClickListener {
            finish()
        }
        binding.noteStartImageButton.setOnClickListener {
            if (binding.noteLinearLayout.visibility != View.VISIBLE) {
                getNoteFilm()
                binding.noteLinearLayout.visibility = View.VISIBLE
            }
            else {
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

            Toast.makeText(applicationContext, getString(R.string.delete_note_film),
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
        filmCardBannerImageView = binding.filmCardBannerImageView
        val putInfoFilms = intent.extras
        if (putInfoFilms != null) {
            binding.filmCardNameTextView.text = putInfoFilms.getString(TITLE, "")
            binding.filmCardRatingTextView.text = putInfoFilms.getDouble(VOTE_AVERAGE).toString()
            binding.filmCardDateTextView.text = putInfoFilms.getString(RELEASE_DATE, "")
            binding.filmCardDescriptionTextView.text = putInfoFilms.getString(OVERVIEW, "")
            idFilms = putInfoFilms.getInt(ID,0)
            Picasso.get().load(IMAGES_PATH + putInfoFilms.getString(POSTER_PATCH))
                .into(filmCardBannerImageView)

        }

    }
}
