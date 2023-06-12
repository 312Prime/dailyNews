package com.example.dailynews.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    // Navigation
    /** 이동 후 종료하기 */
    fun startActivityAndFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }
}