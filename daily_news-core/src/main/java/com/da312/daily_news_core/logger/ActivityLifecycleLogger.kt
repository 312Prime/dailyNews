package com.da312.daily_news_core.logger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityLifecycleLogger(
    private val supplier: LogSupplier
) : Application.ActivityLifecycleCallbacks {

    private val fragmentLogTracker = FragmentLifecycleLogger(supplier)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        supplier.log(
            String.format(
                "onCreate:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )

        if (activity is AppCompatActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                fragmentLogTracker,
                true
            )
        }
    }

    override fun onActivityStarted(activity: Activity) {
        supplier.log(
            String.format(
                "onStart:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )
    }

    override fun onActivityResumed(activity: Activity) {
        supplier.log(
            String.format(
                "onResume:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )
    }

    override fun onActivityPaused(activity: Activity) {
        supplier.log(
            String.format(
                "onPause:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )
    }

    override fun onActivityStopped(activity: Activity) {
        supplier.log(
            String.format(
                "onStop:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        supplier.log(
            String.format(
                "onSaveInstanceState:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )
    }

    override fun onActivityDestroyed(activity: Activity) {
        supplier.log(
            String.format(
                "onDestroy:: %s%s",
                activity::class.java.simpleName,
                "(${activity.hashCode()})"
            )
        )
    }
}