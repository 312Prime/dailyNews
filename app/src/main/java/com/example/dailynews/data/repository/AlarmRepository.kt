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
        // 원래 있던 알람 리스트
        val oldList = if (sharedPreferenceManager.alarmList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.alarmList)
        // 새로운 알람 리스트
        val newList = JsonArray()
        // 반환할 알람 데이터 모델
        val newModel = mutableListOf<AlarmItemsModel>()
        // 새로운 알람 리스트에 기존 알람 추가
        for (i in 0 until oldList.length()) {
            newList.add(oldList.optString(i))
        }
        // 새로운 알람 리스트에 새로운 알람 추가
        newList.add(alarmItemsModel.time + alarmItemsModel.alarmCode.toString() + alarmItemsModel.content)
        // 저장소에 새로운 알람 리스트 덮어쓰기
        sharedPreferenceManager.alarmList = newList.toString()
        // 새로운 알람 모델에 기존 알람을 모델로 변환해 추가
        for (i in 0 until oldList.length()) {
            newModel.add(
                AlarmItemsModel(
                    time = oldList.optString(i).substring(0, 4),
                    alarmCode = oldList.optString(i).substring(4, 13).toInt(),
                    content = if (oldList.optString(i).length > 13) oldList.optString(i)
                        .substring(13) else "",
                )
            )
        }
        return newModel
    }

    fun deleteAlarmList(alarmItemsModel: AlarmItemsModel) {
        val newList = sharedPreferenceManager.alarmList
    }
}