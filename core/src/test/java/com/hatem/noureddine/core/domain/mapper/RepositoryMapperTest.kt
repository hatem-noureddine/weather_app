package com.hatem.noureddine.core.domain.mapper

import com.hatem.noureddine.core.data.local.models.DBLocation
import com.hatem.noureddine.core.data.local.models.DBWeather
import com.hatem.noureddine.core.data.local.models.FeelsLike
import com.hatem.noureddine.core.data.local.models.Temperature
import com.hatem.noureddine.core.data.remote.models.Daily
import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapResponse
import com.hatem.noureddine.core.data.remote.models.Weather
import com.hatem.noureddine.core.domain.models.Location
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random


class RepositoryMapperTest {

    @Test
    fun toLocation() {
        val dbLocation = DBLocation(uid = 1, longitude = 10.10, latitude = 11.11, name = "test")
        val location = dbLocation.toLocation()
        Assert.assertEquals(dbLocation.uid, location.id)
        Assert.assertEquals(dbLocation.longitude, location.longitude, 0.0)
        Assert.assertEquals(dbLocation.latitude, location.latitude, 0.0)
        Assert.assertEquals(dbLocation.name, location.name)
    }

    @Test
    fun toDBLocation() {
        val location = Location(id = 1, longitude = 10.10, latitude = 11.11, name = "test")
        val dbLocation = location.toDBLocation()
        Assert.assertEquals(dbLocation.uid, location.id)
        Assert.assertEquals(dbLocation.longitude, location.longitude, 0.0)
        Assert.assertEquals(dbLocation.latitude, location.latitude, 0.0)
        Assert.assertEquals(dbLocation.name, location.name)
    }

    @Test
    fun toTemperature() {
        val temperature = Temperature(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
        val wTemperature = temperature.toTemperature()
        Assert.assertEquals(temperature.day, wTemperature.day, 0.0)
        Assert.assertEquals(temperature.eve, wTemperature.eve, 0.0)
        Assert.assertEquals(temperature.max, wTemperature.max, 0.0)
        Assert.assertEquals(temperature.min, wTemperature.min, 0.0)
        Assert.assertEquals(temperature.night, wTemperature.night, 0.0)
    }

    @Test
    fun toFeelsLike() {
        val feelsLike = FeelsLike(1.0, 2.0, 3.0, 4.0)
        val wFeelsLike = feelsLike.toFeelsLike()
        Assert.assertEquals(feelsLike.day, wFeelsLike.day, 0.0)
        Assert.assertEquals(feelsLike.eve, wFeelsLike.eve, 0.0)
        Assert.assertEquals(feelsLike.morn, wFeelsLike.morn, 0.0)
        Assert.assertEquals(feelsLike.night, wFeelsLike.night, 0.0)
    }

    @Test
    fun toWeather() {
        val dbWeather = DBWeather(
            1,
            Random.nextLong(),
            Random.nextLong(),
            Random.nextLong(),
            Temperature(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextInt(),
            FeelsLike(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextDouble(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextDouble(),
            Random.nextDouble(),
            Random.nextInt(),
            null,
            Random.nextDouble(),
            "test-description",
            "icon_rain"
        )
        val weather = dbWeather.toWeather()
        Assert.assertEquals(weather.locationUid, dbWeather.locationUid)
        Assert.assertEquals(weather.timestamp, dbWeather.timestamp)
        Assert.assertEquals(weather.sunrise, dbWeather.sunrise)
        Assert.assertEquals(weather.sunset, dbWeather.sunset)

        Assert.assertEquals(weather.temp.day, dbWeather.temp.day, 0.0)
        Assert.assertEquals(weather.temp.eve, dbWeather.temp.eve, 0.0)
        Assert.assertEquals(weather.temp.max, dbWeather.temp.max, 0.0)
        Assert.assertEquals(weather.temp.min, dbWeather.temp.min, 0.0)
        Assert.assertEquals(weather.temp.morn, dbWeather.temp.morn, 0.0)
        Assert.assertEquals(weather.temp.night, dbWeather.temp.night, 0.0)

        Assert.assertEquals(weather.humidity, dbWeather.humidity)

        Assert.assertEquals(weather.feelsLike.day, dbWeather.feelsLike.day, 0.0)
        Assert.assertEquals(weather.feelsLike.morn, dbWeather.feelsLike.morn, 0.0)
        Assert.assertEquals(weather.feelsLike.night, dbWeather.feelsLike.night, 0.0)
        Assert.assertEquals(weather.feelsLike.eve, dbWeather.feelsLike.eve, 0.0)

        Assert.assertEquals(weather.windSpeed, dbWeather.windSpeed, 0.0)
        Assert.assertEquals(weather.windDeg, dbWeather.windDeg)
        Assert.assertEquals(weather.clouds, dbWeather.clouds)
        Assert.assertEquals(weather.dewPoint, dbWeather.dewPoint, 0.0)
        Assert.assertEquals(weather.pop, dbWeather.pop, 0.0)
        Assert.assertEquals(weather.pressure, dbWeather.pressure)
        Assert.assertEquals(weather.rain, dbWeather.rain)
        Assert.assertEquals(weather.uvi, dbWeather.uvi, 0.0)
        Assert.assertEquals(weather.description, dbWeather.description)
        Assert.assertEquals(weather.icon, dbWeather.icon)
    }

    @Test
    fun toDbWeather() {
        val daily = Daily(
            1,
            Random.nextDouble(),
            Random.nextLong(),
            com.hatem.noureddine.core.data.remote.models.FeelsLike(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextInt(),
            Random.nextDouble(),
            Random.nextInt(),
            Random.nextDouble(),
            Random.nextLong(),
            Random.nextLong(),
            com.hatem.noureddine.core.data.remote.models.Temperature(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextDouble(),
            listOf(Weather("test-description", "icon_rain", Random.nextInt(), "main test value")),
            Random.nextInt(),
            Random.nextDouble()
        )
        val dbWeather = daily.toDbWeather(10)
        Assert.assertEquals(10, dbWeather.locationUid)
        Assert.assertEquals(daily.dt, dbWeather.timestamp)
        Assert.assertEquals(daily.sunrise, dbWeather.sunrise)
        Assert.assertEquals(daily.sunset, dbWeather.sunset)

        Assert.assertEquals(daily.temp.day, dbWeather.temp.day, 0.0)
        Assert.assertEquals(daily.temp.eve, dbWeather.temp.eve, 0.0)
        Assert.assertEquals(daily.temp.max, dbWeather.temp.max, 0.0)
        Assert.assertEquals(daily.temp.min, dbWeather.temp.min, 0.0)
        Assert.assertEquals(daily.temp.morn, dbWeather.temp.morn, 0.0)
        Assert.assertEquals(daily.temp.night, dbWeather.temp.night, 0.0)

        Assert.assertEquals(daily.humidity, dbWeather.humidity)

        Assert.assertEquals(daily.feelsLike.day, dbWeather.feelsLike.day, 0.0)
        Assert.assertEquals(daily.feelsLike.morn, dbWeather.feelsLike.morn, 0.0)
        Assert.assertEquals(daily.feelsLike.night, dbWeather.feelsLike.night, 0.0)
        Assert.assertEquals(daily.feelsLike.eve, dbWeather.feelsLike.eve, 0.0)

        Assert.assertEquals(daily.windSpeed, dbWeather.windSpeed, 0.0)
        Assert.assertEquals(daily.windDeg, dbWeather.windDeg)
        Assert.assertEquals(daily.clouds, dbWeather.clouds)
        Assert.assertEquals(daily.dewPoint, dbWeather.dewPoint, 0.0)
        Assert.assertEquals(daily.pop, dbWeather.pop, 0.0)
        Assert.assertEquals(daily.pressure, dbWeather.pressure)
        Assert.assertEquals(daily.rain, dbWeather.rain)
        Assert.assertEquals(daily.uvi, dbWeather.uvi, 0.0)
        Assert.assertEquals(daily.weather.firstOrNull()?.description, dbWeather.description)
        Assert.assertEquals(daily.weather.firstOrNull()?.icon, dbWeather.icon)
    }

    @Test
    fun testToWeather() {
        val daily = Daily(
            1,
            Random.nextDouble(),
            Random.nextLong(),
            com.hatem.noureddine.core.data.remote.models.FeelsLike(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextInt(),
            Random.nextDouble(),
            Random.nextInt(),
            Random.nextDouble(),
            Random.nextLong(),
            Random.nextLong(),
            com.hatem.noureddine.core.data.remote.models.Temperature(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextDouble(),
            listOf(Weather("test-description", "icon_rain", Random.nextInt(), "main test value")),
            Random.nextInt(),
            Random.nextDouble()
        )
        val weather = daily.toWeather(10)
        Assert.assertEquals(10, weather.locationUid)
        Assert.assertEquals(daily.dt, weather.timestamp)
        Assert.assertEquals(daily.sunrise, weather.sunrise)
        Assert.assertEquals(daily.sunset, weather.sunset)

        Assert.assertEquals(daily.temp.day, weather.temp.day, 0.0)
        Assert.assertEquals(daily.temp.eve, weather.temp.eve, 0.0)
        Assert.assertEquals(daily.temp.max, weather.temp.max, 0.0)
        Assert.assertEquals(daily.temp.min, weather.temp.min, 0.0)
        Assert.assertEquals(daily.temp.morn, weather.temp.morn, 0.0)
        Assert.assertEquals(daily.temp.night, weather.temp.night, 0.0)

        Assert.assertEquals(daily.humidity, weather.humidity)

        Assert.assertEquals(daily.feelsLike.day, weather.feelsLike.day, 0.0)
        Assert.assertEquals(daily.feelsLike.morn, weather.feelsLike.morn, 0.0)
        Assert.assertEquals(daily.feelsLike.night, weather.feelsLike.night, 0.0)
        Assert.assertEquals(daily.feelsLike.eve, weather.feelsLike.eve, 0.0)

        Assert.assertEquals(daily.windSpeed, weather.windSpeed, 0.0)
        Assert.assertEquals(daily.windDeg, weather.windDeg)
        Assert.assertEquals(daily.clouds, weather.clouds)
        Assert.assertEquals(daily.dewPoint, weather.dewPoint, 0.0)
        Assert.assertEquals(daily.pop, weather.pop, 0.0)
        Assert.assertEquals(daily.pressure, weather.pressure)
        Assert.assertEquals(daily.rain, weather.rain)
        Assert.assertEquals(daily.uvi, weather.uvi, 0.0)
        Assert.assertEquals(daily.weather.firstOrNull()?.description, weather.description)
        Assert.assertEquals(daily.weather.firstOrNull()?.icon, weather.icon)
    }

    @Test
    fun toDBWeathers() {
        val response = OpenWeatherMapResponse(
            listOf(
                Daily(
                    1,
                    Random.nextDouble(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.FeelsLike(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextLong(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.Temperature(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextDouble(),
                    listOf(
                        Weather(
                            "test-description",
                            "icon_rain",
                            Random.nextInt(),
                            "main test value"
                        )
                    ),
                    Random.nextInt(),
                    Random.nextDouble()
                ), Daily(
                    1,
                    Random.nextDouble(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.FeelsLike(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextLong(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.Temperature(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextDouble(),
                    listOf(
                        Weather(
                            "test-description",
                            "icon_rain",
                            Random.nextInt(),
                            "main test value"
                        )
                    ),
                    Random.nextInt(),
                    Random.nextDouble()
                )
            ),
            Random.nextDouble(),
            Random.nextDouble(),
            "GMT",
            Random.nextInt()
        )

        val locationUid = Random.nextInt()
        val dbWeathers = response.toDBWeathers(
            Location(
                locationUid,
                Random.nextDouble(),
                Random.nextDouble(),
                "test"
            )
        )

        Assert.assertEquals(response.daily.size, dbWeathers.size)
        dbWeathers.forEach {
            Assert.assertEquals(it.locationUid, locationUid)
        }
    }

    @Test
    fun toWeathers() {
        val response = OpenWeatherMapResponse(
            listOf(
                Daily(
                    1,
                    Random.nextDouble(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.FeelsLike(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextLong(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.Temperature(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextDouble(),
                    listOf(
                        Weather(
                            "test-description",
                            "icon_rain",
                            Random.nextInt(),
                            "main test value"
                        )
                    ),
                    Random.nextInt(),
                    Random.nextDouble()
                ), Daily(
                    1,
                    Random.nextDouble(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.FeelsLike(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextInt(),
                    Random.nextDouble(),
                    Random.nextLong(),
                    Random.nextLong(),
                    com.hatem.noureddine.core.data.remote.models.Temperature(
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble(),
                        Random.nextDouble()
                    ),
                    Random.nextDouble(),
                    listOf(
                        Weather(
                            "test-description",
                            "icon_rain",
                            Random.nextInt(),
                            "main test value"
                        )
                    ),
                    Random.nextInt(),
                    Random.nextDouble()
                )
            ),
            Random.nextDouble(),
            Random.nextDouble(),
            "GMT",
            Random.nextInt()
        )

        val locationUid = Random.nextInt()
        val weathers = response.toWeathers(
            Location(
                locationUid,
                Random.nextDouble(),
                Random.nextDouble(),
                "test"
            )
        )

        Assert.assertEquals(response.daily.size, weathers.size)
        weathers.forEach {
            Assert.assertEquals(it.locationUid, locationUid)
        }
    }
}