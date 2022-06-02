package com.kotlinmovie.movies.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.data.RetrofitGivFilmsPopularImpl
import com.kotlinmovie.movies.domain.DataModel
import com.kotlinmovie.movies.domain.FilmListSearch
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import com.kotlinmovie.movies.ui.ActivityStartFilmsCard.Companion.TITLE
import com.kotlinmovie.movies.ui.adapter.SearchFilmAdapter


class SearchDialogFragment : Fragment() {

    private val givRateFilmsTMDB: GivRateFilmsRepoTMDB = RetrofitGivFilmsPopularImpl()
    private var mContext: Context? = null
    private lateinit var adapterSearchFilm: SearchFilmAdapter

    private var searchMoviesPage = 1
    private var searchMoviesIncludeAdult: Boolean = true
    private var searchQuery = ""

    private lateinit var closeButton: ImageButton
    companion object {
        fun newInstance() = SearchDialogFragment()
    }

    private val dataModel: DataModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = context

        val view = inflater.inflate(R.layout.fragment_search_dialog, container, false
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner
        dataModel.sendingSearchRequest.observe(activity as LifecycleOwner) {
            searchQuery = it
        }
        dataModel.positionAdultSwitch.observe(activity as LifecycleOwner) {
            searchMoviesIncludeAdult = it.toBoolean()
        }
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecyclerViewSearch()
        initButton()
    }

    private fun initButton() {
        closeButton = view?.findViewById(R.id.close_imageButton2)!!
        closeButton.setOnClickListener {
            this.requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
        }
    }


    private fun initRecyclerViewSearch() {
        val recyclerView: RecyclerView =
            requireView().findViewById(R.id.search_list_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(mContext, 3)
        adapterSearchFilm = SearchFilmAdapter(mutableListOf()) { filmListSearch ->
            startResultSearchFilm(filmListSearch)
        }
        recyclerView.adapter = adapterSearchFilm


            givRateFilmsTMDB.getSearchMoves(
                searchMoviesPage,
                searchQuery,
                searchMoviesIncludeAdult,
                onSuccess = :: gotListMoviesSearch

            ) {
                Toast.makeText(
                    mContext,
                    "ссылка не существует либо нет подлючения к интернету ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("TAG", "не работает ")
            }


    }

    private fun startResultSearchFilm(filmListSearch: FilmListSearch) {
        startActivity(Intent(mContext,ActivityStartFilmsCard::class.java).apply {
            putExtra(TITLE, filmListSearch.title)
            putExtra(ActivityStartFilmsCard.POSTER_PATCH, filmListSearch.posterPath)
            putExtra(ActivityStartFilmsCard.VOTE_AVERAGE, filmListSearch.voteAverage)
            putExtra(ActivityStartFilmsCard.RELEASE_DATE, filmListSearch.releaseDate)
            putExtra(ActivityStartFilmsCard.OVERVIEW, filmListSearch.overview)
        })


        Toast.makeText(
            mContext,
            "${filmListSearch.title}",
            Toast.LENGTH_LONG
        ).show()
    }
    private fun gotListMoviesSearch(result: MutableList<FilmListSearch>){
        adapterSearchFilm.addMoveSearch(result)
    }

}