package com.example.dailynews.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.databinding.ItemWeatherListBinding
import com.example.dailynews.model.ForecastListModel

class WeatherAdapter() : RecyclerView.Adapter<WeatherAdapter.ListViewHolder>() {

    private var forecastItems= mutableListOf<ForecastListModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherAdapter.ListViewHolder {
        return ListViewHolder(
            binding = ItemWeatherListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ListViewHolder, position: Int) {
        forecastItems.getOrNull(position)?.let {
            holder.bindViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return forecastItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(newList: List<ForecastListModel>){
        with(forecastItems){
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemWeatherListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(data: ForecastListModel){
            with(binding){
//                weatherRecyclerDate =
            }
        }

    }
}