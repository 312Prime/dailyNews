package com.da312.dailynews.tools.logger

import android.util.Log
import com.da312.dailynews.BuildConfig
import com.da312.dailynews.tools.Configuration

object Logger {

    enum class LogType {
        DEBUG,
        INFO,
        ERROR
    }

    fun debug(log : Any , developer: Configuration.Developer? = null) = log(LogType.DEBUG , log , developer)
    fun error(log : Any , developer: Configuration.Developer? = null) = log(LogType.ERROR , log , developer)
    fun info(log : Any , developer: Configuration.Developer? = null) = log(LogType.INFO , log , developer)

    private fun log(logType : LogType, log : Any, developer: Configuration.Developer? = null) {
        if(BuildConfig.DEBUG) {
            Log.d(logType.name , "[DAILY_NEWS][${logType.name}]${developer?.let { "[${it.name}]" }?:""} $log")
        }
    }

}
