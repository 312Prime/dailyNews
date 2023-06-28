package com.example.dailynews.activity

import androidx.lifecycle.MutableLiveData
import com.example.dailynews.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val location = MutableLiveData(Pair(0.0, 0.0))
}