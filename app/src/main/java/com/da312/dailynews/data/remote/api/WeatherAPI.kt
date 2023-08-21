package com.da312.dailynews.data.remote.api

import com.da312.dailynews.model.ForecastModel
import com.da312.dailynews.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// 날씨 API Model
interface WeatherAPI {

    @GET("data/2.5/{path}")
    suspend fun getWeather(
        @Path("path") path: String,
        @Query("q") q: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String,
    ): Response<WeatherModel>

    @GET("data/2.5/{path}")
    suspend fun getForecast(
        @Path("path") path: String,
        @Query("q") q: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String,
    ): Response<ForecastModel>

}