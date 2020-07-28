package com.hatem.noureddine.core.domain.repositories

import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.data.local.dao.WeatherDao
import com.hatem.noureddine.core.data.remote.datasources.WeatherRemoteDataSource
import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapRequest
import com.hatem.noureddine.core.domain.mapper.toDBWeathers
import com.hatem.noureddine.core.domain.mapper.toWeather
import com.hatem.noureddine.core.domain.mapper.toWeathers
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.core.domain.models.Weather
import com.hatem.noureddine.core.domain.utils.performGetOperation
import com.hatem.noureddine.core.domain.utils.performNetworkGetOperation
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal class WeatherRepositoryImp constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherDao
) : WeatherRepository {
    override fun getWeathers(
        coroutineContext: CoroutineContext,
        location: Location
    ): LiveData<out Resource<List<Weather>>> {
        return performGetOperation(
            coroutineContext = coroutineContext,
            databaseQuery = {
                localDataSource.getWeathers(location.id).map { list ->
                    list.map { dbWeather ->
                        dbWeather.toWeather()
                    }
                }
            },
            networkCall = {
                remoteDataSource.getWeatherDetails(
                    OpenWeatherMapRequest(location.latitude, location.longitude)
                )
            },
            saveCallResult = {
                it.toDBWeathers(location).forEach { dbWeather ->
                    localDataSource.insert(dbWeather)
                }
            }
        )
    }

    override fun getWeathers(
        coroutineContext: CoroutineContext,
        longitude: Double,
        latitude: Double
    ): LiveData<out Resource<List<Weather>>> {
        return performNetworkGetOperation(
            coroutineContext = coroutineContext,
            networkCall = {
                remoteDataSource.getWeatherDetails(
                    OpenWeatherMapRequest(latitude, longitude)
                )
            },
            mapResult = {
                it.toWeathers()
            })
    }
}