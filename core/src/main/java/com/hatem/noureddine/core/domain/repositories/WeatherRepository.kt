package com.hatem.noureddine.core.domain.repositories

import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.core.domain.models.Weather
import kotlin.coroutines.CoroutineContext

interface WeatherRepository {

    fun getWeathers(
        coroutineContext: CoroutineContext,
        location: Location
    ): LiveData<out Resource<List<Weather>>>

    fun getWeathers(
        coroutineContext: CoroutineContext,
        longitude: Double,
        latitude: Double
    ): LiveData<out Resource<List<Weather>>>
}