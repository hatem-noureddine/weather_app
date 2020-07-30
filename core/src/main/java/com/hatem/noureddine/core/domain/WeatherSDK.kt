package com.hatem.noureddine.core.domain

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.data.local.WeatherDatabase
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.data.local.dao.WeatherDao
import com.hatem.noureddine.core.data.remote.datasources.WeatherRemoteDataSource
import com.hatem.noureddine.core.data.remote.datasources.WeatherRemoteDataSourceImp
import com.hatem.noureddine.core.data.remote.interceptors.WeatherAppIdInterceptor
import com.hatem.noureddine.core.data.remote.services.WeatherService
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.core.domain.models.Weather
import com.hatem.noureddine.core.domain.repositories.LocationRepository
import com.hatem.noureddine.core.domain.repositories.LocationRepositoryImp
import com.hatem.noureddine.core.domain.repositories.WeatherRepository
import com.hatem.noureddine.core.domain.repositories.WeatherRepositoryImp
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class WeatherSDK(context: Context) {

    private val apiKey: String by lazy {
        context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .let { applicationInfo ->
                applicationInfo.metaData.getString("com.weather.sdk.library_access_key")
                    ?: throw IllegalArgumentException()
            }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            addInterceptor(WeatherAppIdInterceptor(apiKey))
            addInterceptor(OkHttpProfilerInterceptor())
            File(context.cacheDir, "api").let {
                val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
                this.cache(Cache(it, cacheSize))
            }
        }.build()
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    private val weatherService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }

    private val weatherRemoteDataSource: WeatherRemoteDataSource by lazy {
        WeatherRemoteDataSourceImp(weatherService)
    }

    private val database: WeatherDatabase by lazy {
        WeatherDatabase.getDatabase(context)
    }

    private val weatherDao: WeatherDao by lazy {
        database.weatherDao()
    }

    private val locationDao: LocationDao by lazy {
        database.locationDao()
    }

    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImp(weatherRemoteDataSource, weatherDao)
    }

    private val LocationRepository: LocationRepository by lazy {
        LocationRepositoryImp(locationDao)
    }

    /**
     * fetch weathers data for a location
     * @param location Location
     * @return LiveData<out Resource<List<Weather>>>
     */
    fun getWeathers(location: Location): LiveData<out Resource<List<Weather>>> {
        return weatherRepository.getWeathers(location)
    }

    /**
     * get stored location on database
     * @return LiveData<out Resource<List<Location>>>
     */
    fun getLocations(): LiveData<out Resource<List<Location>>> {
        return LocationRepository.getLocations()
    }

    /**
     * insert a new location into database
     * @param location Location
     * @return LiveData<out Resource<Location>>
     */
    fun insertLocation(location: Location): LiveData<out Resource<Location>> {
        return LocationRepository.insertLocation(location)
    }
}