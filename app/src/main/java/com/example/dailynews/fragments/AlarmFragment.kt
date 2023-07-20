package com.example.dailynews.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynews.R
import com.example.dailynews.adapter.AlarmAdapter
import com.example.dailynews.base.BaseFragment
import com.example.dailynews.databinding.FragmentAlarmBinding
import com.example.dailynews.model.AlarmItemsModel
import com.example.dailynews.tools.receiver.AlarmReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

class AlarmFragment : BaseFragment(R.layout.fragment_alarm) {

    private var _binding: FragmentAlarmBinding? = null

    private val viewModel by viewModel<AlarmViewModel>()

    private val alarmAdapter by lazy { AlarmAdapter(requireContext()) }
    private val binding get() = _binding!!

    private lateinit var pendingIntent: PendingIntent
    private val ioScope by lazy { CoroutineScope(Dispatchers.IO) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {
            with(alarmRecyclerView) {
                adapter = alarmAdapter.also { it.initList(mutableListOf()) }
                layoutManager = LinearLayoutManager(requireContext())
            }

            with(alarmConfirmButton) {
                setOnClickListener {
                    val setTime = "2000-00-00 ${alarmTimePicker.hour}:${alarmTimePicker.minute}:00"
                    val currentTime = (
                            LocalDate.now().toString().replace("-", "")
                                .substring(3) + LocalTime.now().toString()
                                .substring(0, 6).replace(":", "")).toInt()
                    val newAlarm = AlarmItemsModel(
                        time = "${alarmTimePicker.hour}" +
                                (if (alarmTimePicker.minute < 10) "0" else "") +
                                "${alarmTimePicker.minute}",
                        content = alarmEditText.text.toString(),
                        alarmCode = currentTime
                    )
                    callAlarm(setTime, currentTime, alarmEditText.text.toString())
                    viewModel.saveAlarmList(newAlarm)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun callAlarm(time: String, alarmCode: Int, content: String) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(context, AlarmReceiver::class.java)

        receiverIntent.apply {
            putExtra("alarmRqCode", alarmCode)
            putExtra("content", content)
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                context,
                alarmCode,
                receiverIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context,
                alarmCode,
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd H:mm:ss")
        var dateTime = Date()
        try {
            dateTime = dateFormat.parse(time) as Date
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = dateTime

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun cancelAlarm(alarmCode: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(
                context,
                alarmCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        alarmManager.cancel(pendingIntent)
    }
}