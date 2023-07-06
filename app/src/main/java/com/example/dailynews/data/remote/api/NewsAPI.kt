package com.example.dailynews.data.remote.api

import com.example.dailynews.model.NewsListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

// 뉴스 API Model
interface NewsAPI {

    @GET("v1/search/{path}")
    suspend fun getNews(
        @Path("path") path: String,
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") pw: String,
        @Query("query") query: String,
    ): Response<NewsListModel>
}