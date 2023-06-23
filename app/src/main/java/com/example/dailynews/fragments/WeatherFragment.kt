package com.example.dailynews.fragments

import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailynews.R
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentWeatherBinding

class WeatherFragment : BaseFragment(R.layout.fragment_todo) {

    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!

    private val locationManager = LocationManager.KEY_LOCATIONS

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {}
    }
}