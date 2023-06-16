package com.example.dailynews.koin

import android.app.Application
import com.bumptech.glide.Glide
import com.example.daily_news_core.logger.ActivityLifecycleLogger
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinApplication : Application() {

    private val activityLifecycleLogger by inject<ActivityLifecycleLogger>()

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidLogger()
            androidContext(this@KoinApplication)
            modules(
                ViewModelModule.module,
                RepositoryModule.module,
                ManagerModule.module,
                LogModule.module,
            )
        }

        registerActivityLifecycleCallbacks(activityLifecycleLogger)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }
}
