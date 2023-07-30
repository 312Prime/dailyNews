package com.example.dailynews.fragments

import androidx.lifecycle.MutableLiveData
import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.WeatherRepository
import com.example.dailynews.model.ForecastModel
import com.example.dailynews.model.WeatherModel
import com.example.dailynews.tools.exceptionManager.ExceptionManager
import com.example.dailynews.tools.logger.Logger
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import org.json.JSONObject

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val exceptionManager: ExceptionManager
) : BaseViewModel() {

    val isSuccessWeather = MutableStateFlow(false)
    val isSuccessForecast = MutableStateFlow(false)
    val responseWeather = MutableStateFlow(false)
    val responseForecast = MutableStateFlow(false)

    val cityName = MutableLiveData<String>()

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
            isSuccessWeather.value = true
            responseWeather.value = true
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
            isSuccessForecast.value = true
            responseForecast.value = true
        }.retry(retries) {
            exceptionManager.delayRetry(it)
        }.catch {
            exceptionManager.log(it)
        }.onCompletion {
            isLoading.value = false
        }.launchIn(ioScope)
    }
}