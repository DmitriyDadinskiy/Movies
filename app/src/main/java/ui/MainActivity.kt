package ui


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
import com.kotlinmovie.movies.databinding.ActivityMainBinding
import data.CLickOnRecommendationImage
import data.FilmsListRecommendation
import data.FilmsListWatchingNow
import domain.RecommendationAdapter
import domain.WatchingNowAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapterWatchingNow = WatchingNowAdapter()
    private val adapterRecommendation = RecommendationAdapter()
    private lateinit var mySnackbarLayot: CoordinatorLayout
    private val imageFilmsWatchingNow = listOf(
        R.drawable.joker,
        R.drawable.joker1,
        R.drawable.joker2,
        R.drawable.joker2,
        R.drawable.joker1,
        R.drawable.joker2,
        R.drawable.joker1,
        R.drawable.joker2,
    )
    private val imageFilmsRecommendation = listOf(
        R.drawable.joker,
        R.drawable.joker1,
        R.drawable.joker2,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

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
        adapterRecommendation.setClickOnRecommendationImage(object : CLickOnRecommendationImage {
            override fun onClick(imageId: Int) {
                intent.setClass(this@MainActivity, ActivityStartFilmsCard::class.java)
                intent.putExtra(CardFilmsFragment.imagefilm, imageId)
                startActivity(intent)

                Toast.makeText(applicationContext, "Рекомендации", Toast.LENGTH_LONG).show()
            }

            override fun onClickImageButton(favoritesID: Int) {
                Snackbar.make(
                    mySnackbarLayot,
                    resources.getString(R.string.button_favorites),
                    Snackbar.LENGTH_INDEFINITE)
                    .setDuration(10000)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                    .show()
            }

        })
        adapterWatchingNow.setClickWatchingNow(object : WatchingNowAdapter.CLickOnWatchingNowImage {
            override fun onClick(imageId: Int) {
                intent.setClass(this@MainActivity, ActivityStartFilmsCard::class.java)
                intent.putExtra(CardFilmsFragment.imagefilm, imageId)
                startActivity(intent)

                Toast.makeText(applicationContext, "Смотрят сейчас", Toast.LENGTH_LONG).show()
            }

            override fun onClickFavorites(favoritesID: Int) {
                Snackbar.make(
                    mySnackbarLayot,
                    resources.getString(R.string.button_favorites),
                    Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show()
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

            for (index in imageFilmsWatchingNow.indices) {
                val filmsListWatchingNow = FilmsListWatchingNow(
                    imageFilmsWatchingNow[index],
                    "Joker $index", "2020", "9.9"
                )
                adapterWatchingNow.addAllFilmsWatchingNow(filmsListWatchingNow)

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_info ->

                Toast.makeText(
                    applicationContext, "not realize",
                    Toast.LENGTH_LONG
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


