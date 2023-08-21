package com.da312.dailynews.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.da312.dailynews.R
import com.da312.dailynews.adapter.AlarmAdapter
import com.da312.dailynews.base.BaseFragment
import com.da312.dailynews.databinding.FragmentAlarmBinding
import com.da312.dailynews.model.AlarmItemsModel
import com.da312.dailynews.tools.receiver.AlarmReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

class AlarmFragment : BaseFragment(R.layout.fragment_alarm) {

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<AlarmViewModel>()

    private val alarmAdapter by lazy { AlarmAdapter(requireContext(), this) }

    private lateinit var pendingIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        setObserver()
        alarmAdapter.initList(viewModel.initAlarmList())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setBinding() {
        with(binding) {
            // RecyclerView 설정
            with(alarmRecyclerView) {
                adapter = alarmAdapter.also { it.initList(mutableListOf()) }
                layoutManager = LinearLayoutManager(requireContext())
            }

            // 알람 추가 버튼
            with(alarmAddButton) {
                setOnClickListener {
                    resetAlarmLayout()
                }
            }

            // 알람 삭제 버튼
            with(alarmCancelButton) {
                setOnClickListener {
                    resetAlarmLayout(false)
                    resetTime()
                }
            }

            // 알람 확인 버튼
            with(alarmConfirmButton) {
                setOnClickListener {
                    // 현재 시간 보다 이른 시간 이면 내일 알람 설정 그렇지 않으면 오늘 알람 설정
                    val setDate =
                        if (alarmTimePicker.hour <= LocalTime.now().hour && alarmTimePicker.minute <= LocalTime.now().minute)
                            LocalDate.now().plusDays(1)
                        else LocalDate.now()

                    // 알람 설정 시간
                    val setTime =
                        "$setDate ${alarmTimePicker.hour}:${alarmTimePicker.minute}:00"

                    // 알람 코드 (현재 시간)
                    val currentTime = (LocalTime.now().toString().replace(":", "").replace(".", "")
                        .substring(0, 9)).toInt()

                    // 알람 모델 생성
                    val newAlarm = AlarmItemsModel(
                        time = (if (alarmTimePicker.hour < 10) "0" else "") + "${alarmTimePicker.hour}" + (if (alarmTimePicker.minute < 10) "0" else "") + "${alarmTimePicker.minute}",
                        content = alarmEditText.text.toString(),
                        alarmCode = currentTime
                    )

                    callAlarm(setTime, currentTime, alarmEditText.text.toString())
                    alarmAdapter.initList(viewModel.saveAlarmList(newAlarm))
                    resetTime()
                    resetAlarmLayout(false)
                }
            }
        }
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.isListEmpty.observe(viewLifecycleOwner) {
                binding.alarmEmptyTextView.visibility = if (it == true) View.VISIBLE else View.GONE
            }
        }
    }

    // 알람 저장
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
                context, alarmCode, receiverIntent, PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context, alarmCode, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT
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

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    }

    // 알람 삭제 팝업
    fun showCancelAlarmDialog(content: String, alarmTime: String, alarmCode: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("알람을 삭제하시겠습니까?").setMessage("$alarmTime \n$content")
            .setPositiveButton("삭제") { _, _ ->
                cancelAlarm(alarmCode)
            }.setNegativeButton("취소") { _, _ ->
            }
        builder.show()
    }

    // 알람 취소
    private fun cancelAlarm(alarmCode: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(
                context, alarmCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        alarmManager.cancel(pendingIntent)
        alarmAdapter.initList(viewModel.deleteAlarmList(alarmCode))
    }

    // 알람 설정 Layout 열기/닫기
    private fun resetAlarmLayout(visibility: Boolean? = true) {
        binding.alarmEditText.setText("")
        binding.alarmAddLayout.visibility = if (visibility == true) View.VISIBLE else View.GONE
    }

    // 알람 설정 TimePicker 초기화
    private fun resetTime() {
        binding.alarmTimePicker.hour = LocalTime.now().hour
        binding.alarmTimePicker.minute = LocalTime.now().minute
    }
}