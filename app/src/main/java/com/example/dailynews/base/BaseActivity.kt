package com.example.dailynews.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d("TAG","$this")
        supportActionBar?.hide()
    }

    // Navigation
    /** 이동 후 종료하기 */
    fun startActivityAndFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }
}