package domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ItemRecommendationBinding
import data.CLickOnRecommendationImage
import data.FilmsListRecommendation



class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.RecommendationHolder>(){
    private val recommendationAdapterList = ArrayList<FilmsListRecommendation>()
    private var onClickListener: CLickOnRecommendationImage? = null

     inner class RecommendationHolder(item: View): RecyclerView.ViewHolder(item) {
        private val filmItemImageRecommendation: ImageView = itemView.findViewById(R.id.film_recommendation_item_image
        )
        private val binding = ItemRecommendationBinding.bind(item)
         private val filmRecommendationFavorites: ImageButton = binding.filmRecommendationCardFavoritesImageButton
        fun bind(recommendation: FilmsListRecommendation) = with(binding){
           filmRecommendationItemImage.setImageResource(recommendation.imageId)
            filmRecommendationNameItemTextView.text = recommendation.filmName
            yearRecommendationFilmTextView.text = recommendation.filmYear
            ratingRecommendationTextView.text = recommendation.rating
        }
        init {
            filmItemImageRecommendation.setOnClickListener{
                onClickListener?.onClick(adapterPosition)
            }
            filmRecommendationFavorites.setOnClickListener{
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
    fun setClickOnRecommendationImage (onClickListener: CLickOnRecommendationImage) {
        this.onClickListener = onClickListener

    }

    fun addAllFilmsRecommendation(recommendation: FilmsListRecommendation){
        recommendationAdapterList.addAll(listOf(recommendation))
        notifyDataSetChanged()
    }


}