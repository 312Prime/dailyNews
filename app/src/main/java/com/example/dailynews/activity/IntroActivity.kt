package com.example.dailynews.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import com.example.dailynews.R
import com.example.dailynews.base.BaseActivity
import com.example.dailynews.databinding.ActivityIntroBinding
import kotlin.concurrent.thread

class IntroActivity : BaseActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private var isReady = false
    private var isStart = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_intro)
        Log.d("DTE","DTE")
        setBinding()
    }

    private fun setBinding() {
        with(binding) {
            with(root) {
                setContentView(this)
            }
        }
//        initSplashScreen()
    }

    private fun initSplashScreen() {
        initData()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (isReady) {
                        // 3초 후 Splash Screen 제거
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready
                        false
                    }
                }
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            errorGuard()

            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 1000L
                isStart = true

                slideUp.doOnEnd {
                    splashScreenView.remove()
                    startMainActivity()
                }

                slideUp.start()
            }
        } else {
            startMainActivity()
        }
    }

    private fun initData() {
        thread(start = true) {
            for (i in 1..3) {
                Thread.sleep(1000)
            }
            isReady = true
        }
    }

    private fun errorGuard() {
        thread(start = true) {
            Thread.sleep(5000)
            if (!isStart) {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivityAndFinish(intent)
    }
}