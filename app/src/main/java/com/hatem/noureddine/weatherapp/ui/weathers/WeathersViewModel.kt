package com.hatem.noureddine.weatherapp.ui.weathers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.core.domain.models.Weather
import com.hatem.noureddine.weatherapp.SampleApplication

class WeathersViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherSDK
        get() = this.getApplication<SampleApplication>().weatherSDK

    fun getWeathers(location: Location): LiveData<out Resource<List<Weather>>> =
        weatherSDK.weatherRepository.getWeathers(location)
}