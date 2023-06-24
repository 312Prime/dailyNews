package com.example.dailynews.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.dailynews.tools.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

// Restful Main Class /추후 수정 필요
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
            .client(
                OkHttpClient
                    .Builder()
                    .addNetworkInterceptor {
                        it.proceed(
                            it.request()
                                .newBuilder()
                                .build()
                        )
                    }
                    .addNetworkInterceptor(chuckerInterceptor)
                    .readTimeout(30L, TimeUnit.SECONDS)
                    .writeTimeout(30L, TimeUnit.SECONDS)
                    .build()
            )
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
}