package com.kotlinmovie.movies.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.app
import com.kotlinmovie.movies.data.movie.FilmCardEntity
import com.kotlinmovie.movies.databinding.ActivityStartFilmsCardBinding
import com.kotlinmovie.movies.domain.DataModel
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import com.kotlinmovie.movies.room.model.NoteFilm
import com.kotlinmovie.movies.room.model.NoteRepo
import com.squareup.picasso.Picasso


class ActivityStartFilmsCard : AppCompatActivity() {

    private lateinit var binding: ActivityStartFilmsCardBinding
    private val noteRepo: NoteRepo by lazy { app.noteFilm }
    private val givRateFilmsTMDB: GivRateFilmsRepoTMDB by lazy { app.givRateFilmsRepoTMDB }
    private val dataModel: DataModel by viewModels()

    companion object {
        const val ID = "id_films"
        const val REFRESH_PERIOD = 6000L
        const val MINIMAL_DISTANCE = 5f
    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
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
        myGeoPosition()

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
                applicationContext, getString(R.string.delete_note_film), Toast.LENGTH_SHORT
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
                applicationContext, "нет подключения к ${it.message}", Toast.LENGTH_SHORT
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
            Picasso.get().load(IMAGES_PATH + result.posterPath).into(filmCardBannerImageView)
            filmCardCountryPasteTextView.text = nameCountry
        }
    }

    private fun viewLocationCountry() {
        binding.filmCardCountryPasteTextView.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_films_card,
                    MapYandexFragment.newInstance()
                )
                .addToBackStack("").commit()
        }
    }

    private fun myGeoPosition() { //Нужно получить свои координаты  до начала загрузки карты и передать их в Мап фрагмент для подгрузки
        val onLocationListener = LocationListener { location ->
            latitude = location.latitude
            dataModel.latitudeMyPosition.value = latitude
            longitude = location.longitude
            dataModel.longitudeMyPosition.value = longitude
        }

        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)

            provider?.let {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    onLocationListener
                )
            }
        }


    }

}
