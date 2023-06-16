package com.example.dailynews.koin

import com.example.daily_news_core.logger.ActivityLifecycleLogger
import com.example.daily_news_core.logger.LogSupplier
import com.example.dailynews.tools.logger.Logger
import org.koin.dsl.module

object LogModule : KoinModule {

    override val module = module {
        single<LogSupplier> { LogSupplierImpl() }
        single { ActivityLifecycleLogger(get()) }
    }
}

internal class LogSupplierImpl : LogSupplier {

    override fun log(message: String) {
        Logger.debug(message)
    }
}