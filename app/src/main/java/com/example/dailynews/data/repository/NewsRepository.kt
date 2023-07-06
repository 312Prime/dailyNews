package com.example.dailynews.data.repository

import com.example.dailynews.base.BaseRepository
import com.example.dailynews.data.remote.RestfulManager
import com.example.dailynews.model.NewsListModel
import com.example.dailynews.tools.logger.Logger

class NewsRepository(
    private val restfulManager: RestfulManager
) : BaseRepository() {

    suspend fun getNewsInfo(query: String): NewsListModel {
        return unWrap(
            restfulManager.newsApi.getNews(
                path = "news",
                query = query,
                id = "YOtntth05ZrTIbj9Imnd",
                pw = "JSgcP_dpbV"
            )
        )
    }
}