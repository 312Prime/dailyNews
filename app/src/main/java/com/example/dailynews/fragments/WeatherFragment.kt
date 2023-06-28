package com.example.dailynews.fragments

import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
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
import com.example.dailynews.tools.logger.Logger
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

// 날씨 Fragment
class WeatherFragment : BaseFragment(R.layout.fragment_todo) {

    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private var currentLat = 0.0
    private var currentLong = 0.0

    private val viewModel by viewModel<WeatherViewModel>()


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = LocationListener {
            currentLat = it.latitude
            currentLong= it.longitude
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        getCityName()
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

    private fun getCityName(): String {
//        locationManager = getSystemService
        var cityName: String?
//        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
//        val address = geoCoder.getFromLocation(lat, long, 1)
//        cityName = address[0].adminArea
//        if (cityName == null) {
//            cityName = address[0].locality
//            if (cityName == null) {
//                cityName = address[0].subAdminArea
//            }
//        }
        return "cityName"
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