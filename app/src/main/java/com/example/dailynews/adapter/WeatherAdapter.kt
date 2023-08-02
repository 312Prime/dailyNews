package com.example.dailynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailynews.R
import com.example.dailynews.databinding.ItemWeatherListBinding
import com.example.dailynews.model.ForecastListModel

class WeatherAdapter(val context: Context) : RecyclerView.Adapter<WeatherAdapter.ListViewHolder>() {

    private var forecastItems = mutableListOf<ForecastListModel>()

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
    fun initList(newList: List<ForecastListModel>) {
        with(forecastItems) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemWeatherListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bindViewHolder(data: ForecastListModel) {
            with(binding) {
                weatherRecyclerDate.text = data.dt_txt.substring(5, 10) +" " + data.dt_txt.substring(11,13) + "시"
                weatherDetailState.text = data.weather?.get(0)?.description
                weatherDetailTemp.text =
                    if (data.main?.temp != null) ("%.1f 'c".format(data.main?.temp!! - 273.15)) else ""
                Glide.with(context).load(
                    context.resources.getDrawable(
                        context.resources.getIdentifier(
                            "icon_" + data.weather?.get(0)?.icon,
                            "drawable",
                            context.packageName
                        )
                    )
                ).placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .into(binding.weatherDetailIcon)
            }
        }

    }
}