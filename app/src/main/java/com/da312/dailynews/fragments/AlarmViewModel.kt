package com.da312.dailynews.fragments

import androidx.lifecycle.asLiveData
import com.da312.dailynews.base.BaseViewModel
import com.da312.dailynews.data.repository.AlarmRepository
import com.da312.dailynews.model.AlarmItemsModel
import kotlinx.coroutines.flow.MutableStateFlow

class AlarmViewModel(
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {

    private val _isListEmpty = MutableStateFlow(false)
    val isListEmpty = _isListEmpty.asLiveData()

    // 알람 리스트 저장
    fun saveAlarmList(alarmItemsModel: AlarmItemsModel): List<AlarmItemsModel> {
        return alarmRepository.storeAlarmList(alarmItemsModel)
            .also { _isListEmpty.value = it.isEmpty() }
    }

    // 알람 리스트 초기화
    fun initAlarmList(): List<AlarmItemsModel> {
        return alarmRepository.initAlarm()
            .also { _isListEmpty.value = it.isEmpty() }
    }

    // 알람 삭제
    fun deleteAlarmList(alarmCode: Int): List<AlarmItemsModel> {
        return alarmRepository.deleteAlarmList(alarmCode)
            .also { _isListEmpty.value = it.isEmpty() }
    }
}