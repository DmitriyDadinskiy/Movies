package domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.ItemWatchingNowBinding
import data.FilmsListWatchingNow

class WatchingNowAdapter:
    RecyclerView.Adapter<WatchingNowAdapter.WatchingHolder>() {
    private val watchingNowAdapterList = ArrayList<FilmsListWatchingNow>()
    private var onClickListenerWatchingNowImage: CLickOnWatchingNowImage? = null


    inner class WatchingHolder(item: View): RecyclerView.ViewHolder(item) {
        private val filmItemImage: ImageView = itemView.findViewById(R.id.film_item_image)
        private val binding = ItemWatchingNowBinding.bind(item)

        fun bind(filmsListWatchingNow: FilmsListWatchingNow)
        = with(binding){
            filmItemImage.setImageResource(filmsListWatchingNow.imageId)
            filmNameItemTextView.text = filmsListWatchingNow.filmName
            yearFilmTextView.text = filmsListWatchingNow.filmYear
            ratingTextView.text = filmsListWatchingNow.rating
        }

        init {
            filmItemImage.setOnClickListener{
                onClickListenerWatchingNowImage?.onClick(adapterPosition)
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
    fun addAllFilmsWatchingNow(filmsListWatchingNow: FilmsListWatchingNow){
        watchingNowAdapterList.addAll(listOf(filmsListWatchingNow))
        notifyDataSetChanged()
    }
    fun setClickWatchingNow(onClickListenerWatchingNowImage: CLickOnWatchingNowImage){
        this.onClickListenerWatchingNowImage = onClickListenerWatchingNowImage

    }
    interface CLickOnWatchingNowImage {
        fun onClick(imageId: Int)

    }

}






