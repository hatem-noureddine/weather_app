package com.hatem.noureddine.core.data.remote.services

import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


internal interface WeatherService {

    /**
     * Call to fetch weather details: currently, daily, hourly, minutely
     * @param values Map<String, String>: list of params
     * @return OpenWeatherMapResponse
     */
    @GET("/data/2.5/onecall")
    suspend fun getWeatherDetails(@QueryMap values: Map<String, String>): Response<OpenWeatherMapResponse>
}