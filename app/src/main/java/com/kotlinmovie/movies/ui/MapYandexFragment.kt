package com.kotlinmovie.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.kotlinmovie.movies.databinding.FragmentMapYandexBinding
import com.kotlinmovie.movies.domain.DataModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView


class MapYandexFragment : Fragment() {

    private var _binding: FragmentMapYandexBinding? = null
    private val binding: FragmentMapYandexBinding
        get() = _binding!!

    private lateinit var mapview: MapView

    companion object {
        fun newInstance() = MapYandexFragment()

    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val dataModel: DataModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapYandexBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataModel.latitudeMyPosition.observe(activity as LifecycleOwner) {
            latitude = it
        }
        dataModel.longitudeMyPosition.observe(activity as LifecycleOwner) {
            longitude= it
        }
        super.onViewCreated(view, savedInstanceState)
        initMapYandex()
    }


    private fun initMapYandex() {

        MapKitFactory.initialize(requireContext())
        mapview = binding.mapView
        mapview.map.move(
            CameraPosition(Point(latitude, longitude), 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}