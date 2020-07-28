package com.hatem.noureddine.core.data.remote.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
internal data class Daily(
    val clouds: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    val dt: Long,
    @Json(name = "feels_like")
    val feelsLike: FeelsLike,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double?,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temperature,
    val uvi: Double,
    val weather: List<Weather>,
    @Json(name = "wind_deg")
    val windDeg: Int,
    @Json(name = "wind_speed")
    val windSpeed: Double
) : Parcelable
