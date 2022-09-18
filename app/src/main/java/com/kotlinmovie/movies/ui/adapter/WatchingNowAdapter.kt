package com.kotlinmovie.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ItemWatchingNowBinding
import com.kotlinmovie.movies.domain.FilmsListWatchingNow
import com.kotlinmovie.movies.ui.IMAGES_PATH
import com.squareup.picasso.Picasso


class WatchingNowAdapter(
    private var watchingNowAdapterList: List<FilmsListWatchingNow>,
    private val onMovieClick: (filmsListWatchingNow: FilmsListWatchingNow) -> Unit
) :
    RecyclerView.Adapter<WatchingNowAdapter.WatchingHolder>() {

    private var onClickListenerWatchingNowImageButton: CLickOnWatchingNowImage? = null



    inner class WatchingHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val filmItemImageView: ImageView = itemView.findViewById(R.id.film_item_imageView)
        private val filmWatchingNowFavorites: ImageButton =
            itemView.findViewById(R.id.film_card_favorites_imageButton)
        private val binding = ItemWatchingNowBinding.bind(item)

        fun bind(filmsListWatchingNow: FilmsListWatchingNow) = with(binding) {

            Picasso.get().load(IMAGES_PATH + filmsListWatchingNow.posterPath)
                .into(this@WatchingHolder.filmItemImageView)

            filmNameItemTextView.text = filmsListWatchingNow.title
            yearFilmTextView.text = filmsListWatchingNow.releaseDate
            ratingTextView.text = filmsListWatchingNow.voteAverage.toString()
            filmItemImageView.setOnClickListener { onMovieClick.invoke(filmsListWatchingNow) }
        }


        init {
            filmWatchingNowFavorites.setOnClickListener {
                onClickListenerWatchingNowImageButton?.onClickFavorites(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_watching_now, parent, false)
        return WatchingHolder(view)
    }

    override fun onBindViewHolder(holder: WatchingHolder, position: Int) {
        holder.bind(watchingNowAdapterList[position])
    }

    override fun getItemCount(): Int {
        return watchingNowAdapterList.size
    }

    fun addAllFilmsWatchingNow(watchingNowAdapterList: List<FilmsListWatchingNow>) {
        this.watchingNowAdapterList = watchingNowAdapterList
        notifyDataSetChanged()
    }


    fun setClickWatchingNow(onClickListenerWatchingNowImage: CLickOnWatchingNowImage) {
        this.onClickListenerWatchingNowImageButton = onClickListenerWatchingNowImage

    }


    interface CLickOnWatchingNowImage {
        fun onClickFavorites(favoritesID: Int)

    }


}











