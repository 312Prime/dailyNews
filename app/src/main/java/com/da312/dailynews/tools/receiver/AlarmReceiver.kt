package com.da312.dailynews.tools.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.da312.dailynews.R
import com.da312.dailynews.tools.service.AlarmService

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var manager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    companion object {
        const val CHANNEL_ID = "channel"
        const val CHANNEL_NAME = "channel1"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )

        builder = NotificationCompat.Builder(context, CHANNEL_ID)

        val intent2 = Intent(context, AlarmService::class.java)
        val requestCode = intent?.extras!!.getInt("alarm_rqCode")
        val title = if (intent.extras!!.getString("content") != "")
            intent.extras!!.getString("content")
        else "설정된 알림 시간 입니다."

        // Activity 시작 Intent 생성
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, requestCode, intent2, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(
                context,
                requestCode,
                intent2,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        // 알람을 매일 설정된 시간마다 울리게 set
        val notification = builder.setContentTitle("DailyNews")
            .setContentText("$title")
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(1, notification)
    }
}