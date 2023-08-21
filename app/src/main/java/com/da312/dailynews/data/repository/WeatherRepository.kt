package com.da312.dailynews.data.repository

import com.da312.dailynews.base.BaseRepository
import com.da312.dailynews.data.remote.RestfulManager
import com.da312.dailynews.model.ForecastModel
import com.da312.dailynews.model.WeatherModel

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