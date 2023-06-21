package com.example.dailynews.activity

import android.os.Bundle
import android.os.PersistableBundle
import com.example.dailynews.base.BaseActivity
import com.example.dailynews.databinding.ActivityMainBinding

class MainActivity: BaseActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
    }

    private fun setBinding(){
        with(binding){
            with(root){
                setContentView(this)
            }
        }
    }
}