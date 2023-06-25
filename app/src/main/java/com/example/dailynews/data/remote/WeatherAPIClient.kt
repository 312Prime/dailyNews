package com.example.dailynews.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherAPIClient {

    fun getClient(url: String): Retrofit {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(CookiesInterceptor())
            .addNetworkInterceptor(CookiesInterceptor()).build()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        return retrofit
    }
}