package com.example.dailynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.databinding.ItemAlarmListBinding
import com.example.dailynews.fragments.AlarmFragment
import com.example.dailynews.model.AlarmItemsModel
import com.example.dailynews.model.AlarmModel
import com.example.dailynews.tools.logger.Logger

class AlarmAdapter(val context: Context, val alarmFragment: AlarmFragment) :
    RecyclerView.Adapter<AlarmAdapter.ListViewHolder>() {

    private var newItems = mutableListOf<AlarmItemsModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            binding = ItemAlarmListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        newItems.getOrNull(position)?.let {
            holder.bindViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return newItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(newList: List<AlarmItemsModel>) {
        with(newItems) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemAlarmListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bindViewHolder(data: AlarmItemsModel) {
            with(binding) {
                alarmListTitle.text = if (data.content == "") "설정된 알람" else data.content
                alarmListTime.text = data.time.substring(0, 2) + " : " + data.time.substring(2, 4)
                alarmListDeleteButton.setOnClickListener {
                    alarmFragment.cancelAlarm(data.alarmCode)
                    notifyDataSetChanged()
                }
            }
        }
    }
}