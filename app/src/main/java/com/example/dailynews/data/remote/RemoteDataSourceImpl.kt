package com.example.dailynews.data.remote

import com.example.dailynews.data.remote.api.WeatherAPIService
import com.example.dailynews.model.ForecastModel
import com.example.dailynews.model.WeatherModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {

    override fun getWeatherInfo(
        jsonObject: JSONObject,
        onResponseListener: (Response<WeatherModel>) -> Unit,
        onFailureListener: (Throwable) -> Unit
    ) {
        val APIService =
            WeatherAPIClient.getClient(jsonObject.get("url").toString())
                .create(WeatherAPIService::class.java)
        APIService.doGetJsonDataWeather(
            jsonObject.get("path").toString(),
            jsonObject.get("q").toString(),
            jsonObject.get("appid").toString()
        ).enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                onResponseListener(response)
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                onFailureListener(t)
            }
        })

    }

    override fun getForecastInfo(
        jsonObject: JSONObject,
        onResponseListener: (Response<ForecastModel>) -> Unit,
        onFailureListener: (Throwable) -> Unit
    ) {
        val APIService =
            WeatherAPIClient.getClient(jsonObject.get("url").toString())
                .create(WeatherAPIService::class.java)
        APIService.doGetJsonDataForecast(
            jsonObject.get("path").toString(),
            jsonObject.get("q").toString(),
            jsonObject.get("appid").toString()
        ).enqueue(object : Callback<ForecastModel> {
            override fun onResponse(call: Call<ForecastModel>, response: Response<ForecastModel>) {
                onResponseListener(response)
            }

            override fun onFailure(call: Call<ForecastModel>, t: Throwable) {
                onFailureListener(t)
            }
        }
        )
    }
}