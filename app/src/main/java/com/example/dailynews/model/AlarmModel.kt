package com.example.dailynews.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmModel(
    @SerializedName("items")
    var items: List<AlarmItemsModel>
) : Parcelable

@Parcelize
data class AlarmItemsModel(
    @SerializedName("time")
    var time: String,
    @SerializedName("type")
    var type: String
) : Parcelable