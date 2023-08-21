package com.da312.dailynews.model

import com.google.gson.annotations.SerializedName

data class ForecastModel(
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("cnt")
    var count: Int?,
    @SerializedName("list")
    var list: List<ForecastListModel>?
)

data class ForecastListModel(
    @SerializedName("dt_txt")
    var dt_txt: String,
    @SerializedName("main")
    var main: WeatherMainModel?,
    @SerializedName("weather")
    var weather: List<WeatherWeatherModel>?
)