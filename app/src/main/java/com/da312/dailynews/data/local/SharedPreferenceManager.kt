package com.da312.dailynews.data.local

import android.content.Context
import androidx.core.content.edit

class SharedPreferenceManager(applicationContext: Context) {

    companion object {
        private const val PREFERENCE_KEY = "DAILY_STORE"
    }

    private enum class PreferenceKeys(val keyName: String) {
        ALARM_KEY("ALARM_KEY"),
        TODO_KEY("TODO_KEY")
    }

    private val store =
        applicationContext.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)

    // 저장된 알람 리스트
    var alarmList: String
        get() = store.getString(PreferenceKeys.ALARM_KEY.keyName, null) ?: ""
        set(value) {
            store.edit(commit = true) {
                this.putString(PreferenceKeys.ALARM_KEY.keyName, value)
            }
        }

    // 저장된 할 일 리스트
    var todoList: String
        get() = store.getString(PreferenceKeys.TODO_KEY.keyName, null) ?: ""
        set(value) {
            store.edit(commit = true) {
                this.putString(PreferenceKeys.TODO_KEY.keyName, value)
            }
        }

    fun clearAlarm(){
        store.edit(commit = true){
            this.remove(alarmList)
        }
    }
    fun clearTodoList(){
        store.edit(commit = true){
            this.remove(todoList)
        }
    }

    // value 모두 삭제
    fun clearAll() {
        store.edit(commit = true) {
            PreferenceKeys.values().forEach {
                this.remove(it.keyName)
            }
        }
    }
}