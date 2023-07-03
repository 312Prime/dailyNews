package com.example.dailynews.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.dailynews.data.remote.api.WeatherAPI
import com.example.dailynews.tools.logger.Logger
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Restful Main Class
class RestfulManager(val context: Context) {

    private val chuckerInterceptor = ChuckerInterceptor
        .Builder(context = context)
        .collector(
            ChuckerCollector(
                context = context,
                showNotification = true,
                retentionPeriod = RetentionManager.Period.ONE_HOUR,
            )
        )
        .maxContentLength(250_000L)
        .alwaysReadResponseBody(true)
        .build()

    // 통신 메인 retroFit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .client(
                OkHttpClient
                    .Builder()
                    .addNetworkInterceptor {
                        it.proceed(
                            it.request()
                                .also { Logger.debug("DTE CC $it") }
                                .newBuilder()
                                .build()
                        )
                    }
                    .addNetworkInterceptor(chuckerInterceptor)
                    .readTimeout(30L, TimeUnit.SECONDS)
                    .writeTimeout(30L, TimeUnit.SECONDS)
                    .build()
            ).addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    }

    // 통신시 log 남기기
    private fun log(request: Request) {
        val url = request.url()
        val headers = request.headers()
        val body = request.body()

        Logger.debug("[RESTFUL]Request Url: $url")
        Logger.debug("[RESTFUL]Request Headers: $headers")
        Logger.debug("[RESTFUL]Request Body: $body")
    }

    val weatherApi by lazy<WeatherAPI> { retrofit.create(WeatherAPI::class.java) }
}