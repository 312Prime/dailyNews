package com.da312.dailynews.activity

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.da312.dailynews.base.BaseActivity
import com.da312.dailynews.databinding.ActivityMainBinding
import com.da312.dailynews.fragments.AlarmFragment
import com.da312.dailynews.fragments.NewsFragment
import com.da312.dailynews.fragments.TodoFragment
import com.da312.dailynews.fragments.WeatherFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

class MainActivity : BaseActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<MainViewModel>()

    private var isReady = false
    private var isStart = false

    private val frameLayoutId
        get() = binding.fragmentFrameLayout.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setObserver()
    }

    // binding 설정
    private fun setBinding() {
        with(binding) {
            setContentView(this.root)

            // 최초 날씨 fragment set
            supportFragmentManager.commit {
                replace(frameLayoutId, getFragment("날씨"))
            }

            // bottom Navigation binding
            mainBottomNavigationView.setOnItemSelectedListener {
                switchToFragment(it.toString())
                true
            }
        }

        initSplashScreen()
    }

    private fun setObserver() {
    }

    // splash 보여주기
    private fun initSplashScreen() {
        initData()

        val content: View = binding.root
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (isReady) {
                        // 1초 후 Splash Screen 제거
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready
                        false
                    }
                }
            }
        )

        // 31 이상 sdk에서만 splash 수행 else 넘어가기
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
                }

                slideUp.start()
            }
        }
    }

    // 데이터 불러오기
    private fun initData() {
        thread(start = true) {
            Thread.sleep(1000)
            isReady = true
        }
    }

    // splash 중 에러 발생시 자동 이동
    private fun errorGuard() {
        thread(start = true) {
            Thread.sleep(5000)
        }
    }

    // bottom navigation 전환에 따른 fragment 전환
    private fun switchToFragment(fragmentId: String) {
        supportFragmentManager.commit {
            replace(
                frameLayoutId, getFragment(fragmentId)
            )
        }
    }

    // bottom navigation 을 통해 fragment 이동
    private fun getFragment(fragmentId: String): Fragment {
        return when (fragmentId) {
            "날씨" -> WeatherFragment()
            "뉴스" -> NewsFragment()
            "알람" -> AlarmFragment()
            "할일" -> TodoFragment()
            else -> WeatherFragment()
        }
    }
}