package com.kotlinmovie.movies.ui


import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.app
import com.kotlinmovie.movies.data.ConnectivityManagerCheckInternet
import com.kotlinmovie.movies.data.MAIN_SERVICE_GET_EVENT
import com.kotlinmovie.movies.data.MyIntentServiceLog
import com.kotlinmovie.movies.databinding.ActivityMainBinding
import com.kotlinmovie.movies.domain.CLickOnRecommendationImage
import com.kotlinmovie.movies.domain.FilmsListRecommendation
import com.kotlinmovie.movies.domain.FilmsListWatchingNow
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import com.kotlinmovie.movies.ui.adapter.RecommendationAdapter
import com.kotlinmovie.movies.ui.adapter.WatchingNowAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapterWatchingNow = WatchingNowAdapter()
    private val adapterRecommendation = RecommendationAdapter()
    private lateinit var mySnackbarLayot: CoordinatorLayout
    private val imageFilmsRecommendation = listOf(
        R.drawable.joker,
        R.drawable.joker1,
        R.drawable.joker2,
    )
    private val receiver = ConnectivityManagerCheckInternet()
    private val givRateFilmsTMDB: GivRateFilmsRepoTMDB by lazy { app.givRateFilmsRepoTMDB }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))//подписка на широковещательные сообщения

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
        mySnackbarLayot = binding.snackbarLayout
    }

    private fun startCardFilmFragment() {
        val intent = intent
        val intentServiceLog = Intent(this, MyIntentServiceLog::class.java)
        adapterRecommendation.setClickOnRecommendationImage(object : CLickOnRecommendationImage {
            override fun onClickImages(imageId: Int) {
                intent.setClass(this@MainActivity, ActivityStartFilmsCard::class.java)
                intent.putExtra(CardFilmsFragment.imagefilm, imageId)
                startActivity(intent)
                startService(intentServiceLog.putExtra(MAIN_SERVICE_GET_EVENT,
                    "открыта карточка фильма $imageId"))
                Toast.makeText(applicationContext, "Рекомендации", Toast.LENGTH_LONG).show()
            }

            override fun onClickImageButton(favoritesID: Int) {
                Snackbar.make(
                    mySnackbarLayot,
                    resources.getString(R.string.button_favorites),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setDuration(10000)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .show()
                startService(intentServiceLog.putExtra(MAIN_SERVICE_GET_EVENT,
                    "фильм $favoritesID добавлен в избранное"))
            }

        })
        adapterWatchingNow.setClickWatchingNow(object : WatchingNowAdapter.CLickOnWatchingNowImage {
            override fun onClickImage(imageId: Int) {
                intent.setClass(this@MainActivity, ActivityStartFilmsCard::class.java)
                intent.putExtra(CardFilmsFragment.imagefilm, imageId)
                startActivity(intent)
                startService(intentServiceLog.putExtra(MAIN_SERVICE_GET_EVENT,
                    "открыта карточка фильма $imageId"))
                Toast.makeText(applicationContext, "Смотрят сейчас", Toast.LENGTH_LONG).show()
            }

            override fun onClickFavorites(favoritesID: Int) {
                Snackbar.make(
                    mySnackbarLayot,
                    resources.getString(R.string.button_favorites),
                    Snackbar.LENGTH_LONG
                )
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show()
                startService(intentServiceLog.putExtra(MAIN_SERVICE_GET_EVENT,
                    "фильм $favoritesID добавлен в избранное"))
            }

        })
    }

    private fun initViewRecommendation() {
        binding.apply {
            recommendationRecyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )
            recommendationRecyclerView.adapter = adapterRecommendation
            for (index in imageFilmsRecommendation.indices) {
                val recommendation = FilmsListRecommendation(
                    imageFilmsRecommendation[index],
                    "Joker $index", "2020", "8.8"
                )
                adapterRecommendation.addAllFilmsRecommendation(recommendation)
            }
        }
    }

    private fun initViewWatchingNow() {

        binding.apply {
            watchingNowRecyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            watchingNowRecyclerView.adapter = adapterWatchingNow

            Thread {
                val categoryFilms = listOf<FilmsListWatchingNow>()
                val filmsList = givRateFilmsTMDB.getRateFilms(categoryFilms,
                    onError = {
                        Snackbar.make(
                            mySnackbarLayot,
                            "ссылка не существует либо нет подлючения к интернету ${it.message}",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setDuration(10000)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .show()

                    })
                runOnUiThread {
                    adapterWatchingNow.addAllFilmsWatchingNow(filmsList)
                }
            }.start()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_info ->
                Toast.makeText(
                    applicationContext, "not realize",
                    Toast.LENGTH_SHORT
                ).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchText = search.actionView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


}

private fun View.setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {

}


