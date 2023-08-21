package com.da312.dailynews.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    // Navigation
    /** 이동 후 종료하기 */
    fun startActivityAndFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }
}