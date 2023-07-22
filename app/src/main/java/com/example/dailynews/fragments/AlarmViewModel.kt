package com.example.dailynews.fragments

import com.example.dailynews.base.BaseViewModel
import com.example.dailynews.data.repository.AlarmRepository
import com.example.dailynews.model.AlarmItemsModel

class AlarmViewModel(
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {

    // 알람 리스트 저장
    fun saveAlarmList(alarmItemsModel: AlarmItemsModel) {
        alarmRepository.storeAlarmList(alarmItemsModel)
    }
}