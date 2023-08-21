package com.da312.dailynews.data.repository

import com.da312.dailynews.base.BaseRepository
import com.da312.dailynews.data.local.SharedPreferenceManager
import com.da312.dailynews.model.AlarmItemsModel
import com.google.gson.JsonArray
import org.json.JSONArray

class AlarmRepository(
    private val sharedPreferenceManager: SharedPreferenceManager
) : BaseRepository() {

    // 알람 목록 저장
    fun storeAlarmList(alarmItemsModel: AlarmItemsModel): List<AlarmItemsModel> {
        // 원래 있던 알람 리스트
        var oldList = if (sharedPreferenceManager.alarmList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.alarmList)
        // 새로운 알람 리스트
        val newList = JsonArray()
        // 반환할 알람 데이터 모델
        val newModel = mutableListOf<AlarmItemsModel>()
        // 새로운 알람 list 에 기존 알람 추가
        for (i in 0 until oldList.length()) newList.add(oldList.optString(i))
        // 새로운 알람 목록에 새로운 알람 추가
        newList.add(alarmItemsModel.time + alarmItemsModel.alarmCode.toString() + alarmItemsModel.content)
        // storage 에 새로운 알람 리스트 덮어쓰기
        sharedPreferenceManager.alarmList = newList.toString()
        // 새로운 알람 모델에 기존 알람을 모델로 변환해 추가
        oldList = JSONArray(sharedPreferenceManager.alarmList)
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
        // 정렬 후 반환
        return newModel.apply { sortBy { it.time } }
    }

    // 알람 목록 초기화
    fun initAlarm(): List<AlarmItemsModel> {
        val alarmList = if (sharedPreferenceManager.alarmList == "") JSONArray()
        else JSONArray(sharedPreferenceManager.alarmList)
        val alarmModel = mutableListOf<AlarmItemsModel>()
        for (i in 0 until alarmList.length()) {
            alarmModel.add(
                AlarmItemsModel(
                    time = alarmList.optString(i).substring(0, 4),
                    alarmCode = alarmList.optString(i).substring(4, 13).toInt(),
                    content = if (alarmList.optString(i).length > 13) alarmList.optString(i)
                        .substring(13) else "",
                )
            )
        }
        return alarmModel.apply { sortBy { it.time } }
    }

    // 알람 삭제
    fun deleteAlarmList(alarmCode: Int): List<AlarmItemsModel> {
        val oldList = JSONArray(sharedPreferenceManager.alarmList)
        val newList = JsonArray()
        val newModel = mutableListOf<AlarmItemsModel>()
        for (i in 0 until oldList.length()) {
            if (alarmCode != oldList.optString(i).substring(4, 13).toInt()) {
                newList.add(oldList.optString(i))
                newModel.add(
                    AlarmItemsModel(
                        time = oldList.optString(i).substring(0, 4),
                        alarmCode = oldList.optString(i).substring(4, 13).toInt(),
                        content = if (oldList.optString(i).length > 13) oldList.optString(i)
                            .substring(13) else "",
                    )
                )
            }
        }
        sharedPreferenceManager.alarmList = newList.toString()
        return newModel.apply { sortBy { it.time } }
    }
}