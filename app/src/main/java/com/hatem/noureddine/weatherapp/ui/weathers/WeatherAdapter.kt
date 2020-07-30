package com.hatem.noureddine.weatherapp.ui.weathers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatem.noureddine.core.domain.models.Weather

class WeatherAdapter(private val list: List<Weather> = listOf()) :
    RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherViewHolder(parent)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}