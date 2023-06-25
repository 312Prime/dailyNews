package com.example.dailynews.data.remote

import com.example.dailynews.model.ForecastModel
import com.example.dailynews.model.WeatherModel
import retrofit2.Response
import org.json.JSONObject

interface RemoteDataSource {

    fun getWeatherInfo(
        jsonObject: JSONObject,
        onResponse: (Response<WeatherModel>) -> Unit,
        onFailureListener: (Throwable) -> Unit
    )

    fun getForecastInfo(
        jsonObject: JSONObject,
        onResponse: (Response<ForecastModel>) -> Unit,
        onFailureListener: (Throwable) -> Unit
    )
}