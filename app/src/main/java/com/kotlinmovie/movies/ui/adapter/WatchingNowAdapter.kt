package com.kotlinmovie.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ItemWatchingNowBinding
import com.kotlinmovie.movies.domain.FilmsListWatchingNow


class WatchingNowAdapter :
    RecyclerView.Adapter<WatchingNowAdapter.WatchingHolder>() {
    private val watchingNowAdapterList = ArrayList<FilmsListWatchingNow>()
    private var onClickListenerWatchingNowImage: CLickOnWatchingNowImage? = null


    inner class WatchingHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val filmItemImage: ImageView = itemView.findViewById(R.id.film_item_image)
        private val filmWatchingNowFavorites: ImageButton =
            itemView.findViewById(R.id.film_card_favorites_imageButton)
        private val binding = ItemWatchingNowBinding.bind(item)

        fun bind(filmsListWatchingNow: FilmsListWatchingNow) = with(binding) {
            filmItemImage.setImageResource(R.drawable.joker)
            filmItemImage.setImageResource(R.drawable.joker1)
            filmItemImage.setImageResource(R.drawable.joker2)
            filmNameItemTextView.text = filmsListWatchingNow.title
            yearFilmTextView.text = filmsListWatchingNow.release_date
            ratingTextView.text = filmsListWatchingNow.vote_average.toString()
        }


        init {
            filmItemImage.setOnClickListener {
                onClickListenerWatchingNowImage?.onClickImage(adapterPosition)
            }
            filmWatchingNowFavorites.setOnClickListener {
                onClickListenerWatchingNowImage?.onClickFavorites(adapterPosition)
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

    fun addAllFilmsWatchingNow(filmsListWatchingNow: MutableList<FilmsListWatchingNow>) {
        watchingNowAdapterList.addAll(filmsListWatchingNow)
        notifyDataSetChanged()
    }


    fun setClickWatchingNow(onClickListenerWatchingNowImage: CLickOnWatchingNowImage) {
        this.onClickListenerWatchingNowImage = onClickListenerWatchingNowImage

    }

    interface CLickOnWatchingNowImage {
        fun onClickImage(imageId: Int)
        fun onClickFavorites(favoritesID: Int)

    }


}








