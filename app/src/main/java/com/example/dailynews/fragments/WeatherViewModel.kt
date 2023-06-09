package com.example.dailynews.fragments

import androidx.lifecycle.MutableLiveData
import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.WeatherRepository
import com.example.dailynews.model.ForecastModel
import com.example.dailynews.model.WeatherModel
import com.example.dailynews.tools.logger.Logger
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import org.json.JSONObject

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    val isSuccessWeather = MutableLiveData<Boolean>()
    val isSuccessForecast = MutableLiveData<Boolean>()
    val responseWeather = MutableLiveData<WeatherModel>()
    val responseForecast = MutableLiveData<ForecastModel>()

    val cityName = MutableLiveData("Seoul")

    fun getWeatherInfoView(cityName: String, appid: String) {
        //  진행 중 작업 취소
        job.cancelChildren()
        // 서버 통신 : 날씨 API 불러오기
        flow {
            weatherRepository.getWeatherInfo(cityName = cityName, appid = appid).also { emit(it) }
        }.onStart { }.onEach { data ->
            isSuccessWeather.postValue(true)
            responseWeather.postValue(data)
        }.launchIn(ioScope)
    }

    fun getForecastInfoView(cityName: String, appid: String) {
        //  진행 중 작업 취소
        job.cancelChildren()
        // 서버 통신 : 날씨 API 불러오기
        flow {
            weatherRepository.getForecastInfo(cityName = cityName, appid = appid).also { emit(it) }
        }.onStart { }.onEach { data ->
            isSuccessForecast.postValue(true)
            responseForecast.postValue(data)
        }.launchIn(ioScope)
    }
}