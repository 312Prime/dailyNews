package com.example.dailynews.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    protected val job = SupervisorJob()

    protected val ioScope get() = CoroutineScope(job) + Dispatchers.IO
    protected val retries: Long = 2

    protected val isLoading = MutableStateFlow(true)
    val loading = isLoading.asLiveData()

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}