package com.hatem.noureddine.weatherapp

import android.app.Application
import com.hatem.noureddine.core.domain.WeatherSDK


class SampleApplication : Application() {

    val weatherSDK by lazy {
        WeatherSDK(this)
    }
}