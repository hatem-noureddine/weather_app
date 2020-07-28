package com.hatem.noureddine.core.domain.mapper

import com.hatem.noureddine.core.data.local.models.DBLocation
import com.hatem.noureddine.core.data.local.models.DBWeather
import com.hatem.noureddine.core.data.local.models.FeelsLike
import com.hatem.noureddine.core.data.local.models.Temperature
import com.hatem.noureddine.core.data.remote.models.Daily
import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapResponse
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Weather

/**
 * transform [DBLocation] model to [Location] model
 * @receiver DBLocation
 * @return Location
 */
internal fun DBLocation.toLocation(): Location = Location(uid, longitude, latitude, name)

/**
 * transform [Location] model to [DBLocation] model
 * @receiver Location
 * @return DBLocation
 */
internal fun Location.toDBLocation(): DBLocation =
    DBLocation(uid = id, longitude = longitude, latitude = latitude, name = name)

/**
 * transform [Temperature] model to [Weather.Temperature] model
 * @receiver Temperature
 * @return Weather.Temperature
 */
internal fun Temperature.toTemperature(): Weather.Temperature =
    Weather.Temperature(day, eve, max, min, morn, night)

/**
 * transform [FeelsLike] model to [Weather.FeelsLike]
 * @receiver FeelsLike
 * @return Weather.FeelsLike
 */
internal fun FeelsLike.toFeelsLike(): Weather.FeelsLike = Weather.FeelsLike(day, eve, morn, night)

/**
 * transform [DBWeather] model to [Weather] model
 * @receiver DBWeather
 * @return Weather
 */
internal fun DBWeather.toWeather(): Weather = Weather(
    locationUid,
    timestamp,
    sunrise,
    sunset,
    temp.toTemperature(),
    humidity,
    feelsLike.toFeelsLike(),
    windSpeed,
    windDeg,
    clouds,
    dewPoint,
    pop,
    pressure,
    rain,
    uvi,
    description,
    icon
)

/**
 * transform [Daily] model to [DBWeather] model
 * @receiver Daily
 * @param locationID Int
 * @return DBWeather
 */
internal fun Daily.toDbWeather(locationID: Int): DBWeather {
    val weather = weather.firstOrNull()
        ?: throw IllegalArgumentException("Daily weather should at least have one weather item")
    return DBWeather(
        locationID,
        dt,
        sunrise,
        sunset,
        Temperature(
            temp.day,
            temp.eve,
            temp.max,
            temp.min,
            temp.morn,
            temp.night
        ),
        humidity,
        FeelsLike(
            feelsLike.day,
            feelsLike.eve,
            feelsLike.morn,
            feelsLike.night
        ),
        windSpeed,
        windDeg,
        clouds,
        dewPoint,
        pop,
        pressure,
        rain,
        uvi,
        weather.description,
        weather.icon
    )
}

/**
 * transform [OpenWeatherMapResponse] model to list of [DBWeather]
 * @receiver OpenWeatherMapResponse
 * @param location Location
 * @return List<DBWeather>
 */
internal fun OpenWeatherMapResponse.toDBWeathers(location: Location): List<DBWeather> =
    daily.map { it.toDbWeather(location.id) }


/**
 * transform [Daily] model to [Weather] model
 * @receiver Daily
 * @param locationID Int
 * @return Weather
 */
internal fun Daily.toWeather(locationID: Int = 0): Weather {
    val weather = weather.firstOrNull()
        ?: throw IllegalArgumentException("Daily weather should at least have one weather item")
    return Weather(
        locationID,
        dt,
        sunrise,
        sunset,
        Weather.Temperature(
            temp.day,
            temp.eve,
            temp.max,
            temp.min,
            temp.morn,
            temp.night
        ),
        humidity,
        Weather.FeelsLike(
            feelsLike.day,
            feelsLike.eve,
            feelsLike.morn,
            feelsLike.night
        ),
        windSpeed,
        windDeg,
        clouds,
        dewPoint,
        pop,
        pressure,
        rain,
        uvi,
        weather.description,
        weather.icon
    )
}

/**
 * transform [OpenWeatherMapResponse] model to list of [Weather]
 * @receiver OpenWeatherMapResponse
 * @param location Location?
 * @return List<Weather>
 */
internal fun OpenWeatherMapResponse.toWeathers(location: Location? = null): List<Weather> =
    daily.map { it.toWeather(location?.id ?: 0) }