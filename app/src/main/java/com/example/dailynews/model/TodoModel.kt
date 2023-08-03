package com.example.dailynews.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("isComplete")
    var isComplete: Boolean
) : Parcelable