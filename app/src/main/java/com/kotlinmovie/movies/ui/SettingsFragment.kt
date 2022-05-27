package com.kotlinmovie.movies.ui


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.kotlinmovie.movies.R
import com.kotlinmovie.movies.databinding.FragmentSettingsBinding
import com.kotlinmovie.movies.domain.DataModel

private const val SHARE_PREF_NAME = "SHARE_PREF_NAME"
class SettingsFragment : Fragment(R.layout.fragment_settings) {


    private var binding: FragmentSettingsBinding? = null
    private var positionSwitch = false
    private val preferences: SharedPreferences by lazy {
        this.requireActivity().getSharedPreferences(
            SHARE_PREF_NAME, Context.MODE_PRIVATE)
    }

    private val dataModel: DataModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        dataModel.positionAdultSwitch.value = positionSwitch.toString()
        init()
    }

    private fun init() {
        initButton()
    }

    private fun initButton() {

        binding?.adultSwitch?.setOnCheckedChangeListener { buttonView, isChecked ->
            positionSwitch = isChecked

        }
        binding?.closeSettingImageButton?.setOnClickListener {
            this.requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
        }


    }

    override fun onStart() {
        binding?.adultSwitch?.isChecked = preferences.getBoolean(SHARE_PREF_NAME, false)

        super.onStart()

    }

    override fun onStop() {
        preferences.edit().let {
            it.putBoolean(SHARE_PREF_NAME, positionSwitch)
            it.commit()
        }
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}