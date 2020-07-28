package com.hatem.noureddine.core.data.mapper

import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapRequest
import org.junit.Assert
import org.junit.Test

class WeatherRequestMapperTest {

    @Test
    fun toQueryMap() {
        val request = OpenWeatherMapRequest(10.10, 12.12)
        val queryMap = request.toQueryMap()
        Assert.assertEquals(request.latitude.toString(), queryMap["lat"])
        Assert.assertEquals(request.longitude.toString(), queryMap["lon"])
        val exclude = request.exclude.joinToString(",", transform = { it.value })
        Assert.assertEquals(exclude, queryMap["exclude"])
    }
}