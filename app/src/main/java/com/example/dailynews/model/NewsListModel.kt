package com.example.dailynews.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsListModel(
    @SerializedName("items")
    var items: List<NewsItemsModel>,
) : Parcelable

@Parcelize
data class NewsItemsModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("link")
    var link: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("pubDate")
    var pubDate: String,
) : Parcelable