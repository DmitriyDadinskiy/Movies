package com.kotlinmovie.movies.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.data.RetrofitGivFilmsPopularImpl
import com.kotlinmovie.movies.databinding.FragmentSearchDialogBinding
import com.kotlinmovie.movies.domain.DataModel
import com.kotlinmovie.movies.domain.FilmListSearch
import com.kotlinmovie.movies.domain.GivRateFilmsRepoTMDB
import com.kotlinmovie.movies.ui.adapter.SearchFilmAdapter


class SearchDialogFragment : Fragment() {

    private var _binding: FragmentSearchDialogBinding? = null
    private val binding: FragmentSearchDialogBinding
        get() = _binding!!

    private val givRateFilmsTMDB: GivRateFilmsRepoTMDB = RetrofitGivFilmsPopularImpl()
    private var mContext: Context? = null
    private lateinit var adapterSearchFilm: SearchFilmAdapter

    private var searchMoviesPage = 1
    private var searchMoviesIncludeAdult: Boolean = true
    private var searchQuery = ""

    companion object {
        fun newInstance() = SearchDialogFragment()
    }

    private val dataModel: DataModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = context
        _binding = FragmentSearchDialogBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.searchFilmProgressBar.visibility = View.VISIBLE
        binding.closeImageButton.setOnClickListener {
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
            onSuccess = ::gotListMoviesSearch

        ) {
            Toast.makeText(
                mContext,
                "ссылка не существует либо нет подключения к интернету ${it.message}",
                Toast.LENGTH_LONG
            ).show()
            Log.e("TAG", "не работает ")
        }


    }

    private fun startResultSearchFilm(filmListSearch: FilmListSearch) {
        startActivity(Intent(mContext, ActivityStartFilmsCard::class.java).apply {
            putExtra(ActivityStartFilmsCard.ID, filmListSearch.id)
        })


        Toast.makeText(
            mContext,
            "${filmListSearch.title}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun gotListMoviesSearch(result: MutableList<FilmListSearch>) {
        binding.searchFilmProgressBar.visibility = View.INVISIBLE
        adapterSearchFilm.addMoveSearch(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}