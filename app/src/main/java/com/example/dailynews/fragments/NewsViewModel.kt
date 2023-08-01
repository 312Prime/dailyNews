package com.example.dailynews.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.NewsRepository
import com.example.dailynews.model.NewsItemsModel
import com.example.dailynews.model.NewsListModel
import com.example.dailynews.tools.exceptionManager.ExceptionManager
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

class NewsViewModel(
    private val newsRepository: NewsRepository, private val exceptionManager: ExceptionManager
) : BaseViewModel() {
    private val _responseNews = MutableStateFlow<NewsListModel?>(null)
    val responseNews = _responseNews.asLiveData()

    val searchingMessage = MutableLiveData("")

    fun getNews(searchingMessage: String) {
        //   진행 중 작업 취소
        job.cancelChildren()
        //  서버 통신 : 뉴스 API 불러오기
        flow {
            newsRepository.getNewsInfo(searchingMessage).also { emit(it) }
        }.onStart {
            isLoading.value = true
        }.onEach { data ->
            _responseNews.value = data
        }.retry(retries) {
            exceptionManager.delayRetry(it)
        }.catch {
            exceptionManager.log(it)
        }.onCompletion {
            isLoading.value = false
        }.launchIn(ioScope)
    }
}