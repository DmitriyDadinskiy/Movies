package com.kotlinmovie.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ItemRecommendationBinding
import com.kotlinmovie.movies.domain.FilmsListRecommendation
import com.kotlinmovie.movies.ui.IMAGES_PATH
import com.squareup.picasso.Picasso


class RecommendationAdapter(
    private var recommendationAdapterList: List<FilmsListRecommendation>,
    private val onMovieClickRecommendation: (filmsListRecommendation:
                                             FilmsListRecommendation) -> Unit) :
    RecyclerView.Adapter<RecommendationAdapter.RecommendationHolder>() {

    private var onClickListener: ClickOnRecommendationImage? = null

    inner class RecommendationHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemRecommendationBinding.bind(item)
        private val filmRecommendationFavorites: ImageButton =
            binding.filmRecommendationCardFavoritesImageButton
        private val filmItemImageRecommendation: ImageView = binding.filmRecommendationItemImage

        fun bind(recommendation: FilmsListRecommendation) = with(binding) {

            Picasso.get().load(IMAGES_PATH + recommendation.posterPath)
                .into(this@RecommendationHolder.filmItemImageRecommendation)

            filmRecommendationNameItemTextView.text = recommendation.title
            yearRecommendationFilmTextView.text = recommendation.releaseDate
            ratingRecommendationTextView.text = recommendation.voteAverage.toString()

            filmItemImageRecommendation.setOnClickListener {
                onMovieClickRecommendation.invoke(recommendation)
            }
        }

        init {
            filmRecommendationFavorites.setOnClickListener {
                onClickListener?.onClickImageButton(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation, parent, false)
        return RecommendationHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationHolder, position: Int) {
        holder.bind(recommendationAdapterList[position])

    }

    override fun getItemCount(): Int {
        return recommendationAdapterList.size
    }

    fun setClickOnRecommendationImage(onClickListener: ClickOnRecommendationImage) {
        this.onClickListener = onClickListener

    }

    fun addAllFilmsRecommendation(recommendation: List<FilmsListRecommendation>) {
        this.recommendationAdapterList = recommendation
        notifyDataSetChanged()
    }

    interface ClickOnRecommendationImage {
        fun onClickImageButton(favoritesID: Int)

    }


}

