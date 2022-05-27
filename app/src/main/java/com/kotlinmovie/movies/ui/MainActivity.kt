package com.kotlinmovie.movies.ui


import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.app
import com.kotlinmovie.movies.data.ConnectivityManagerCheckInternet
import com.kotlinmovie.movies.databinding.ActivityMainBinding
import com.kotlinmovie.movies.domain.*
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.ID
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.OVERVIEW
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.POSTER_PATCH
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.RELEASE_DATE
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.TITLE
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.VOTE_AVERAGE
import com.kotlinmovie.movies.ui.adapter.RecommendationAdapter

import com.kotlinmovie.movies.ui.adapter.WatchingNowAdapter

const val IMAGES_PATH = "https://image.tmdb.org/t/p/w342"
const val API_KEY = "feec2f259352fcecc420544fb3ba88de"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapterWatchingNow: WatchingNowAdapter
    private lateinit var adapterRecommendation: RecommendationAdapter
    private lateinit var mySnackbarLayout: CoordinatorLayout

    private val receiver = ConnectivityManagerCheckInternet()
    private val givRateFilmsTMDB: GivRateFilmsRepoTMDB by lazy { app.givRateFilmsRepoTMDB }

    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(receiver,
            IntentFilter(ConnectivityManager
                .CONNECTIVITY_ACTION))//подписка на широковещательные сообщения

        init()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun init() {
        initViewWatchingNow()
        initViewRecommendation()
        startCardFilmFragment()
        initButton()

    }


    private fun initButton() {
        mySnackbarLayout = binding.snackbarLayout
    }

    private fun startCardFilmsWatchingNow(filmsListWatchingNow: FilmsListWatchingNow) {

        startActivity(Intent(this@MainActivity, ActivityStartFilmsCard::class.java)
            .apply {
                putExtra(POSTER_PATCH, filmsListWatchingNow.posterPath)
                putExtra(TITLE, filmsListWatchingNow.title)
                putExtra(VOTE_AVERAGE, filmsListWatchingNow.voteAverage)
                putExtra(RELEASE_DATE, filmsListWatchingNow.releaseDate)
                putExtra(OVERVIEW, filmsListWatchingNow.overview)
                putExtra(ID, filmsListWatchingNow.id)
            })

        val intentServiceLog = Intent(this, MyIntentServiceLog::class.java)
        startService(
            intentServiceLog.putExtra(MAIN_SERVICE_GET_EVENT,
                "открыта карточка фильма ID ${filmsListWatchingNow.id} " +
                        "${filmsListWatchingNow.title}"
            )
        )
        Toast.makeText(applicationContext, "${filmsListWatchingNow.title}",
            Toast.LENGTH_SHORT).show()
    }

    private fun startCardFilmsRecommendation(filmsListRecommendation: FilmsListRecommendation) {

        startActivity(Intent(this@MainActivity, ActivityStartFilmsCard::class.java)
            .apply {
                putExtra(POSTER_PATCH, filmsListRecommendation.posterPath)
                putExtra(TITLE, filmsListRecommendation.title)
                putExtra(VOTE_AVERAGE, filmsListRecommendation.voteAverage)
                putExtra(RELEASE_DATE, filmsListRecommendation.releaseDate)
                putExtra(OVERVIEW, filmsListRecommendation.overview)
            })

        val intentServiceLog = Intent(this, MyIntentServiceLog::class.java)
        startService(
            intentServiceLog.putExtra(MAIN_SERVICE_GET_EVENT,
                "открыта карточка фильма  ${filmsListRecommendation.title}"))
        Toast.makeText(applicationContext, "${filmsListRecommendation.title}",
            Toast.LENGTH_SHORT).show()
    }


    private fun startCardFilmFragment() {
        val intentServiceLog = Intent(this, MyIntentServiceLog::class.java)
        adapterRecommendation.setClickOnRecommendationImage(object :
            RecommendationAdapter.ClickOnRecommendationImage {
            override fun onClickImageButton(favoritesID: Int) {
                Snackbar.make(
                    mySnackbarLayout,
                    resources.getString(R.string.button_favorites),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setDuration(10000)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .show()
                startService(
                    intentServiceLog.putExtra(
                        MAIN_SERVICE_GET_EVENT,
                        "фильм $favoritesID добавлен в избранное"
                    )
                )
            }
        })

        adapterWatchingNow.setClickWatchingNow(object : WatchingNowAdapter.CLickOnWatchingNowImage {
            override fun onClickFavorites(favoritesID: Int) {
                Snackbar.make(
                    mySnackbarLayout,
                    resources.getString(R.string.button_favorites),
                    Snackbar.LENGTH_LONG
                )
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show()
                startService(
                    intentServiceLog.putExtra(
                        MAIN_SERVICE_GET_EVENT,
                        "фильм добавлен в избранное"
                    )
                )
            }

        })
    }

    private fun initViewRecommendation() {
        binding.apply {
            recommendationRecyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )

            adapterRecommendation = RecommendationAdapter(listOf())
            { filmsListRecommendation -> startCardFilmsRecommendation(filmsListRecommendation) }
            recommendationRecyclerView.adapter = adapterRecommendation

            givRateFilmsTMDB.getTopRatedFilms(
                onSuccess = ::gotListMoviesTopRated,
            ) {
                Toast.makeText(
                    applicationContext, "нет подключения к ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun gotListMoviesTopRated(result: List<FilmsListRecommendation>) {
        adapterRecommendation.addAllFilmsRecommendation(result)
    }

    private fun initViewWatchingNow() {

        binding.apply {
            watchingNowRecyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false
            )

            adapterWatchingNow = WatchingNowAdapter(listOf())
            { filmsListWatchingNow -> startCardFilmsWatchingNow(filmsListWatchingNow) }
            watchingNowRecyclerView.adapter = adapterWatchingNow

            givRateFilmsTMDB.getPopularFilms(
                onSuccess = ::gotListMoviesPopular,
            ) {
                Snackbar.make(mySnackbarLayout,
                    "ссылка не существует либо нет подлючения к интернету ${it.message}",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setDuration(10000)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show()
            }
        }

    }

    private fun gotListMoviesPopular(result: List<FilmsListWatchingNow>) {
        adapterWatchingNow.addAllFilmsWatchingNow(result)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_info ->
                Toast.makeText(applicationContext, "not realize",
                    Toast.LENGTH_SHORT
                ).show()

            R.id.menu_settings ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment())
                    .commit()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: $query")
                dismissKeyboardShortcutsHelper()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.design_bottom_sheet, SearchDialogFragment())
                    .commit()
                dataModel.sendingSearchRequest.value = newText
                Log.d(TAG, "onQueryTextChange: $newText")

                return false

            }

        })

        return super.onCreateOptionsMenu(menu)

    }
}




