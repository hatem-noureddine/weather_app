package com.hatem.noureddine.core.domain.repositories

import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.core.domain.models.Weather

interface WeatherRepository {

    fun getWeathers(location: Location): LiveData<out Resource<List<Weather>>>
}