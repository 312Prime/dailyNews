package com.example.dailynews.fragments

import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.AlarmRepository
import com.example.dailynews.model.AlarmItemsModel

class AlarmViewModel(
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {

    // 알람 리스트 저장
    fun saveAlarmList(alarmItemsModel: AlarmItemsModel): List<AlarmItemsModel> {
        return alarmRepository.storeAlarmList(alarmItemsModel)
    }

    // 알람 리스트 초기화
    fun initAlarmList(): List<AlarmItemsModel> {
        return alarmRepository.initAlarm()
    }

    // 알람 삭제
    fun deleteAlarmList(alarmCode: Int): List<AlarmItemsModel>{
        return alarmRepository.deleteAlarmList(alarmCode)
    }

    // 모든 알람 삭제
    fun deleteAllAlarm(){
        alarmRepository.deleteAllAlarm()
    }
}