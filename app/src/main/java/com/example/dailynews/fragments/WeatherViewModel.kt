package com.example.dailynews.fragments

import androidx.lifecycle.MutableLiveData
import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.WeatherRepository
import com.example.dailynews.model.ForecastModel
import com.example.dailynews.model.WeatherModel
import com.example.dailynews.tools.logger.Logger
import org.json.JSONObject

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    val isSuccessWeather = MutableLiveData(false)
    val isSuccessForecast = MutableLiveData(false)
    val responseWeather = MutableLiveData<WeatherModel>()
    val responseForecast = MutableLiveData<ForecastModel>()

    fun getWeatherInfoView(jsonObject: JSONObject) {
        Logger.debug("DTE getWeatherInfoView() - jsonObject : $jsonObject")

        weatherRepository.getWeatherInfo(jsonObject = jsonObject,
            onResponse = {
                if (it.isSuccessful) {
                    Logger.debug("DTE getWeatherInfoView() - Success : ${it.body()}")
                    isSuccessWeather.value = true
                    responseWeather.value = it.body()
                }
            },
            onFailure = {
                it.printStackTrace()
                Logger.debug("DTE getWeatherInfoView() - Failure : ${it.message}")

            }
        )

    }

    fun getForecastInfoView(jsonObject: JSONObject) {
        Logger.debug("getForecastInfoView() - jsonObject : $jsonObject")

        weatherRepository.getForecastInfo(jsonObject = jsonObject,
            onResponse = {
                if (it.isSuccessful) {
                    isSuccessForecast.value = true
                    Logger.debug("getForecastInfoView() - Success : ${it.body()}")
                    responseForecast.value = it.body()
                }
            },
            onFailure = {
                it.printStackTrace()
                Logger.debug("getForecastInfoView() - Failure : ${it.message}")
            }
        )

    }
}