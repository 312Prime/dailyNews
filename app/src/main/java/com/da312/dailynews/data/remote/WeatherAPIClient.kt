package com.da312.dailynews.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherAPIClient {

    fun getClient(url: String): Retrofit {
//        val chuckerInterceptor = ChuckerInterceptor
//            .Builder(context)
//            .collector(
//                ChuckerCollector(
//                    context = context,
//                    showNotification = true,
//                    retentionPeriod = RetentionManager.Period.ONE_HOUR,
//                )
//            )
//            .maxContentLength(250_000L)
//            .alwaysReadResponseBody(true)
//            .build()

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(CookiesInterceptor())
            .addNetworkInterceptor(CookiesInterceptor())
//            .addNetworkInterceptor(chuckerInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        return retrofit
    }
}