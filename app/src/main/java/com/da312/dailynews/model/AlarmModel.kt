package com.da312.dailynews.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// 알람 리스트 데이터 모델
@Parcelize
data class AlarmModel(
    @SerializedName("items")
    var items: List<AlarmItemsModel>
) : Parcelable

// 알람 데이터 모델
@Parcelize
data class AlarmItemsModel(
    @SerializedName("time")
    var time: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("alarmCode")
    var alarmCode: Int
) : Parcelable