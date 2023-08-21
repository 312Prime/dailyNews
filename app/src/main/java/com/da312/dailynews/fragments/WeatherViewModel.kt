package com.da312.dailynews.fragments

import androidx.lifecycle.asLiveData
import com.da312.dailynews.base.BaseViewModel
import com.da312.dailynews.data.repository.WeatherRepository
import com.da312.dailynews.model.ForecastModel
import com.da312.dailynews.model.WeatherModel
import com.da312.dailynews.tools.exceptionManager.ExceptionManager
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val exceptionManager: ExceptionManager
) : BaseViewModel() {

    private val _responseWeather = MutableStateFlow<WeatherModel?>(null)
    val responseWeather = _responseWeather.asLiveData()
    private val _responseForecast = MutableStateFlow<ForecastModel?>(null)
    val responseForecast = _responseForecast.asLiveData()

    private val _cityName = MutableStateFlow("")
    val cityName = _cityName.asLiveData()

    fun setCityName(newCityName: String) {
        _cityName.value = newCityName
    }

    // 날씨 정보 가져오기
    fun getWeatherInfoView(cityName: String, appid: String) {
        //  진행 중 작업 취소
        job.cancelChildren()
        // 서버 통신 : 날씨 API 불러오기
        flow {
            weatherRepository.getWeatherInfo(cityName = cityName, appid = appid).also { emit(it) }
        }.onStart {
            isLoading.value = true
        }.onEach { data ->
            _responseWeather.value = data
        }.retry(retries) {
            exceptionManager.delayRetry(it)
        }.catch {
            exceptionManager.log(it)
        }.onCompletion {
            isLoading.value = false
        }.launchIn(ioScope)
    }

    // 날씨 예보 가져오기
    fun getForecastInfoView(cityName: String, appid: String) {
        //  진행 중 작업 취소
        job.cancelChildren()
        // 서버 통신 : 날씨 API 불러오기
        flow {
            weatherRepository.getForecastInfo(cityName = cityName, appid = appid).also { emit(it) }
        }.onStart {
            isLoading.value = true
        }.onEach { data ->
            _responseForecast.value = data
        }.retry(retries) {
            exceptionManager.delayRetry(it)
        }.catch {
            exceptionManager.log(it)
        }.onCompletion {
            isLoading.value = false
        }.launchIn(ioScope)
    }
}