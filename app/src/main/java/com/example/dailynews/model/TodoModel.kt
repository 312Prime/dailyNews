package com.example.dailynews.model

import com.google.gson.annotations.SerializedName

data class TodoModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("isComplete")
    var isComplete: Boolean
)