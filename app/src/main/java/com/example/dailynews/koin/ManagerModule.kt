package com.example.dailynews.koin

import com.example.dailynews.data.local.SharedPreferenceManager
import com.example.dailynews.data.remote.RestfulManager
import com.example.dailynews.tools.exceptionManager.ExceptionManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object ManagerModule {
    val module = module {
        single { RestfulManager(androidApplication()) }
        single { SharedPreferenceManager(androidApplication()) }
        single { ExceptionManager()}
    }
}