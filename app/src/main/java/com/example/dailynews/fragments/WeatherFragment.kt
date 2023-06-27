package com.example.dailynews.fragments

import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.dailynews.R
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentWeatherBinding
import com.example.dailynews.model.WeatherModel
import com.example.dailynews.tools.customDialog.CustomDialog
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

// 날씨 Fragment
class WeatherFragment : BaseFragment(R.layout.fragment_todo) {

    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<WeatherViewModel>()

    @RequiresApi(Build.VERSION_CODES.S)
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
        initWeatherInfoViewModel()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {}
    }

    private fun initWeatherInfoViewModel() {
        val jsonObject = JSONObject()
        jsonObject.put("url", getString(R.string.weather_url))
        jsonObject.put("path", "weather")
        jsonObject.put("q", "Seoul")
        jsonObject.put("appid", getString(R.string.weather_key))
        viewModel.getWeatherInfoView(jsonObject)
    }

    private fun observeData() {
        viewModel.isSuccessWeather.observe(
            viewLifecycleOwner, Observer { it ->
                if (it) {
                    viewModel.responseWeather.observe(
                        viewLifecycleOwner, Observer {
                            setWeatherData(it)
                        }
                    )
                } else {
                    val customDialog = CustomDialog(requireContext())
                    customDialog.show(getString(R.string.app_name), "현재 날씨 조회 실패")
                }
            }
        )
    }

    private fun setWeatherData(model: WeatherModel) {
//        Glide.with(this).load(
//            resources.getDrawable(
//                resources.getIdentifier(
//                    "icon_" + model.weather[0].icon,
//                    "drawable",
//
//                )
//            )
//        ).placeholder(R.drawable.ic_launcher)
//            .error(R.drawable.ic_launcher)
    }
}