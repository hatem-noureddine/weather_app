package com.hatem.noureddine.core.data.remote.datasources

import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapRequest
import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapResponse
import com.hatem.noureddine.core.data.remote.models.Response

/**
 * Weather remote data source to fetch data from network
 */
internal interface WeatherRemoteDataSource {
    /**
     * Call to fetch weather details: currently, daily, hourly, minutely
     * @param param OpenWeatherMapRequest
     * @return OpenWeatherMapResponse
     */
    suspend fun getWeatherDetails(param: OpenWeatherMapRequest): Response<OpenWeatherMapResponse>
}