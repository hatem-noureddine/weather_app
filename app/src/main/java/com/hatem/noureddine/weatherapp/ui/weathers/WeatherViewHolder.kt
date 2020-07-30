package com.hatem.noureddine.weatherapp.ui.weathers

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.hatem.noureddine.core.domain.models.Weather
import com.hatem.noureddine.weatherapp.R
import com.hatem.noureddine.weatherapp.utils.inflateItem
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class WeatherViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup) : this(parent inflateItem R.layout.activity_weather_item)

    private val statusView: AppCompatImageView = itemView.findViewById(R.id.weather_status)

    private val dateView: MaterialTextView = itemView.findViewById(R.id.weather_date)

    private val tempView: MaterialTextView = itemView.findViewById(R.id.weather_temp)

    fun onBind(item: Weather) {
        Glide.with(statusView).load(item.iconUrl).into(statusView)
        dateView.text = formatDate(item.timestamp)
        tempView.text = "${item.temp.day.toInt()}°"
    }


    private fun formatDate(milliseconds: Long): String {
        val date: LocalDate =
            Instant.ofEpochMilli(milliseconds).atZone(ZoneId.of("UTC")).toLocalDate()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE\ndd LLLL")
        return date.format(formatter)
    }

    private fun handleTemperature(temp: Weather.Temperature): String {
        //when (LocalTime.now().hour){
        //    in 0 until 12 ->temp.m
        //}
        return ""
    }
}