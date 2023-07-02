package com.example.dailynews.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("name")
    var name: String?,
    @SerializedName("weather")
    var weather: List<WeatherWeatherModel>,
    @SerializedName("main")
    var main: WeatherMainModel,
    @SerializedName("wind")
    var wind: WeatherWindModel,
    @SerializedName("sys")
    var sys: WeatherSysModel,
    @SerializedName("clouds")
    var clouds: WeatherCloudsModel
)

data class WeatherWeatherModel(
    @SerializedName("main")
    var main: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("icon")
    var icon: String?
)

data class WeatherMainModel(
    @SerializedName("temp")
    var temp: Float?,
    @SerializedName("humidity")
    var humidity: Float?
)

data class WeatherWindModel(
    @SerializedName("speed")
    var speed: Float?
)

data class WeatherSysModel(
    @SerializedName("country")
    var country: String?
)

data class WeatherCloudsModel(
    @SerializedName("all")
    var all: Float?
)