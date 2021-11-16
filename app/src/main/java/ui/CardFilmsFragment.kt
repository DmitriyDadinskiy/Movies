package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.FragmentCardFilmsBinding


class CardFilmsFragment : Fragment(R.layout.fragment_card_films) {

    private var viewBinding: FragmentCardFilmsBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentCardFilmsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}