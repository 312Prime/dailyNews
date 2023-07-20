package com.example.dailynews.data.repository

import com.example.dailynews.base.BaseRepository
import com.example.dailynews.data.local.SharedPreferenceManager
import com.example.dailynews.model.AlarmItemsModel
import com.example.dailynews.tools.logger.Logger
import com.google.gson.JsonArray
import org.json.JSONArray

class AlarmRepository(
    private val sharedPreferenceManager: SharedPreferenceManager
) : BaseRepository() {

    fun storeAlarmList(alarmItemsModel: AlarmItemsModel): List<AlarmItemsModel> {
        val oldList = if (sharedPreferenceManager.alarmList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.alarmList)
        val newList = JsonArray()
        val newModel = mutableListOf<AlarmItemsModel>()
        for (i in 0 until oldList.length()) {
            newList.add(oldList.optString(i))
        }
        newList.add(alarmItemsModel.time + alarmItemsModel.alarmCode.toString() + alarmItemsModel.content)
        sharedPreferenceManager.alarmList = newList.toString()
        for (i in 0..oldList.length()) {
            newModel.add(
                AlarmItemsModel(
                    time = oldList.optString(i).substring(0, 3),
                    alarmCode = oldList.optString(i).substring(4, 13).toInt(),
                    content = oldList.optString(i).substring(14)?:"",
                )
            )
        }
        return newModel
    }

    fun deleteAlarmList(alarmItemsModel: AlarmItemsModel) {
        val newList = sharedPreferenceManager.alarmList
    }
}