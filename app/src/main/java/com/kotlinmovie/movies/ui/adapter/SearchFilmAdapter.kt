package com.kotlinmovie.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ItemSearchBinding
import com.kotlinmovie.movies.domain.FilmListSearch
import com.kotlinmovie.movies.ui.IMAGES_PATH
import com.squareup.picasso.Picasso


class SearchFilmAdapter (
    private var searchAdapterList: MutableList<FilmListSearch>,
    private val onMoveClick: (filmListSearch: FilmListSearch) -> Unit
        ): RecyclerView.Adapter<SearchFilmAdapter.SearchHolder>() {

    inner class SearchHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemSearchBinding.bind(item)
        private val searchItemImageView = binding.searchFilmItemImageView

        fun bind(filmListSearch: FilmListSearch) = with(binding)  {
            Picasso.get().load(IMAGES_PATH+filmListSearch.posterPath)
                .into(this@SearchHolder.searchItemImageView)
            searchFilmNameItemTextView.text=filmListSearch.title
            searchYearFilmTextView.text = filmListSearch.releaseDate
            searchRatingTextView.text = filmListSearch.voteAverage.toString()

            searchFilmItemImageView.setOnClickListener {
                onMoveClick.invoke(filmListSearch)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SearchFilmAdapter.SearchHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search,parent,false)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchFilmAdapter.SearchHolder, position: Int) {
        holder.bind(searchAdapterList[position])
    }

    override fun getItemCount(): Int {
        return searchAdapterList.size
    }
    fun addMoveSearch(filmListSearch: MutableList<FilmListSearch>){
        this.searchAdapterList = filmListSearch
    }

}