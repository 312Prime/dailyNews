package com.example.dailynews.data.repository

import com.example.dailynews.base.BaseRepository
import com.example.dailynews.data.remote.RestfulManager
import com.example.dailynews.model.ForecastModel
import com.example.dailynews.model.WeatherModel
import com.example.dailynews.tools.logger.Logger

class WeatherRepository(
    private val restfulManager: RestfulManager
) : BaseRepository() {

    suspend fun getWeatherInfo(cityName: String, appid: String): WeatherModel {
        return unWrap(
            restfulManager.weatherApi.getWeather(
                path = "weather",
                q = cityName,
                lang = "kr",
                appid = appid,
            )
        )
    }

    suspend fun getForecastInfo(cityName: String, appid: String): ForecastModel {
        return unWrap(
            restfulManager.weatherApi.getForecast(
                path = "forecast",
                q = cityName,
                lang = "kr",
                appid = appid
            )
        )
    }
}