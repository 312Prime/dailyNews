package com.example.dailynews.data.repository

import com.example.dailynews.base.BaseRepository
import com.example.dailynews.data.local.SharedPreferenceManager
import com.example.dailynews.model.AlarmItemsModel
import com.example.dailynews.model.AlarmModel
import com.google.gson.JsonArray

class AlarmRepository(
    private val sharedPreferenceManager: SharedPreferenceManager
) : BaseRepository() {

    fun storeAlarmList(alarmItemsModel: AlarmItemsModel): AlarmModel {
        val newList = JsonArray()
        newList.add(sharedPreferenceManager.alarmList)
        newList.add(alarmItemsModel.alarmCode.toString() + alarmItemsModel.content + alarmItemsModel.time)
        sharedPreferenceManager.alarmList = newList.toString()
        return newList
    }

    fun deleteAlarmList(alarmItemsModel: AlarmItemsModel) {
        val newList = sharedPreferenceManager.alarmList
    }
}