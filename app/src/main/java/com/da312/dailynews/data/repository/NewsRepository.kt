package com.da312.dailynews.data.repository

import com.da312.dailynews.base.BaseRepository
import com.da312.dailynews.data.remote.RestfulManager
import com.da312.dailynews.model.NewsListModel

class NewsRepository(
    private val restfulManager: RestfulManager
) : BaseRepository() {

    // 뉴스 정보 가져오기
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