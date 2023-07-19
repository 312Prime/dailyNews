package com.example.dailynews.data.local

import android.content.Context
import androidx.core.content.edit

class SharedPreferenceManager(applicationContext: Context) {

    companion object {
        private const val PREFERENCE_KEY = "DAILY_STORE"
    }

    private enum class PreferenceKeys(val keyName: String) {
        ALARM_KEY("ALARM_KEY")
    }

    private val store =
        applicationContext.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)

    var alarmList: String
        get() = store.getString(PreferenceKeys.ALARM_KEY.keyName, null) ?: ""
        set(value) {
            store.edit(commit = true) {
                this.putString(PreferenceKeys.ALARM_KEY.keyName, value)
            }
        }
}