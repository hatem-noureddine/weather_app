package com.hatem.noureddine.core.data.remote.datasources

import com.hatem.noureddine.core.data.mapper.toQueryMap
import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapRequest
import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapResponse
import com.hatem.noureddine.core.data.remote.models.Response
import com.hatem.noureddine.core.data.remote.services.WeatherService

/**
 * Weather remote data source to fetch data from network
 */
internal class WeatherRemoteDataSourceImp(private val service: WeatherService) : BaseDataSource(),
    WeatherRemoteDataSource {

    /**
     * Call to fetch weather details: currently, daily, hourly, minutely
     * @param param OpenWeatherMapRequest
     * @return OpenWeatherMapResponse
     */
    override suspend fun getWeatherDetails(param: OpenWeatherMapRequest): Response<OpenWeatherMapResponse> {
        return getResult { service.getWeatherDetails(param.toQueryMap()) }
    }
}