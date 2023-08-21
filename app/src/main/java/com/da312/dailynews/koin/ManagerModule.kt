package com.da312.dailynews.koin

import com.da312.dailynews.data.local.SharedPreferenceManager
import com.da312.dailynews.data.remote.RestfulManager
import com.da312.dailynews.tools.exceptionManager.ExceptionManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object ManagerModule {
    val module = module {
        single { RestfulManager(androidApplication()) }
        single { SharedPreferenceManager(androidApplication()) }
        single { ExceptionManager()}
    }
}