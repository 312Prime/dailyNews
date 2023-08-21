package com.da312.dailynews.koin

import com.da312.daily_news_core.logger.ActivityLifecycleLogger
import com.da312.daily_news_core.logger.LogSupplier
import com.da312.dailynews.tools.logger.Logger
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