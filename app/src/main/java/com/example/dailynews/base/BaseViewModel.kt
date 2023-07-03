package com.example.dailynews.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    protected val job = SupervisorJob()

    protected val ioScope get() = CoroutineScope(job) + Dispatchers.IO
    protected val retries: Long = 2

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}