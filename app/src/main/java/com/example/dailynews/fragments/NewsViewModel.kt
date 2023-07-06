package com.example.dailynews.fragments

import androidx.lifecycle.MutableLiveData
import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.NewsRepository
import com.example.dailynews.model.NewsItemsModel
import com.example.dailynews.model.NewsListModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class NewsViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val isSuccessNews = MutableLiveData<Boolean>()
    val responseNews = MutableLiveData<NewsListModel>()

    val searchingMessage = MutableLiveData("")

    fun getNews(searchingMessage: String) {
        //   진행 중 작업 취소
        job.cancelChildren()
        //  서버 통신 : 뉴스 API 불러오기
        flow {
            newsRepository.getNewsInfo(searchingMessage).also { emit(it) }
        }.onStart { }.onEach { data ->
            isSuccessNews.postValue(true)
            responseNews.postValue(data)
        }.launchIn(ioScope)
    }
}