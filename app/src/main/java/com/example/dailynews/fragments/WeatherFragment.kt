package com.example.dailynews.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.dailynews.R
import com.example.dailynews.adapter.WeatherAdapter
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentWeatherBinding
import com.example.dailynews.model.WeatherModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.Locale

// 날씨 Fragment
class WeatherFragment : BaseFragment(R.layout.fragment_weather) {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var locationManager: LocationManager

    private val viewModel by viewModel<WeatherViewModel>()

    private val weatherAdapter by lazy { WeatherAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(
            inflater, container, false
        )
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager

        lifecycleScope.launchWhenStarted { setObserver() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        getCityName()
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 위치 권한이 없을 경우 권한 요청
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_LOCATION_PERMISSION
            )
        } else {
            getCityName()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {
            with(weatherLoadingLottie) {
                playAnimation()
            }

            with(weatherRecyclerView) {
                adapter = weatherAdapter.also { it.initList(mutableListOf()) }
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun setObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.weatherLoadingFrameLayout.isVisible = it
        }

        // 도시 정보 observe
        var currentCityName = ""
        viewModel.cityName.observe(viewLifecycleOwner) { cityName ->
            binding.weatherCityName.text = cityName
            if (viewModel.cityName.value != currentCityName) {
                currentCityName = viewModel.cityName.value!!
                // 날씨 정보 호출
                viewModel.getWeatherInfoView(cityName, getString(R.string.weather_key))
            }
        }

        viewModel.responseWeather.observe(viewLifecycleOwner) { model ->
            if (model != null) {
                // 날씨 예보 호출
                viewModel.getForecastInfoView(
                    viewModel.cityName.value!!, getString(R.string.weather_key)
                )
                setWeatherData(model)
                binding.weatherTemperatureState.text =
                    if (model.main.temp == null) "미확인" else "%.1f 'c".format(model.main.temp!! - 273.15)
                binding.weatherCloudShapeState.text = model.weather[0].description
                binding.weatherWindState.text = model.wind.speed.toString() + " m/s"
                binding.weatherCloudState.text = model.clouds.all.toString() + " %"
                binding.weatherHumidity.text = model.main.humidity.toString() + " %"
            }
        }

        viewModel.responseForecast.observe(viewLifecycleOwner) { model ->
            if (model?.list != null) weatherAdapter.initList(model.list!!)
        }
    }

    // 도시 이름 가져오기
    @SuppressLint("MissingPermission")
    private fun getCityName() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener
            )
        } else {
            Toast.makeText(requireContext(), "위치 서비스를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 날씨 아이콘 가져오기
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setWeatherData(model: WeatherModel) {
        Glide.with(this).load(
            resources.getDrawable(
                resources.getIdentifier(
                    "icon_" + model.weather[0].icon, "drawable", requireActivity().packageName
                )
            )
        ).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher)
            .into(binding.weatherIcon)
    }

    // 위도 경도로 도시 이름 확인
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude

            val geocoder = Geocoder(requireContext(), Locale.ENGLISH)
            val geocoderDefault = Geocoder(requireContext(), Locale.getDefault())
            try {
                val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses.isNotEmpty()) {
                    val cityNameDefault =
                        geocoderDefault.getFromLocation(latitude, longitude, 1)[0].locality
                    viewModel.setCityName(cityNameDefault)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}