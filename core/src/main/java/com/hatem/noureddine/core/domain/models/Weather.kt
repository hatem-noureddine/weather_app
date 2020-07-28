package com.hatem.noureddine.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val locationUid: Int,
    val timestamp: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temperature,
    val humidity: Int,
    val feelsLike: FeelsLike,
    val windSpeed: Double,
    val windDeg: Int,
    val clouds: Int,
    val dewPoint: Double,
    val pop: Double,
    val pressure: Int,
    val rain: Double?,
    val uvi: Double,
    val description: String,
    val icon: String
) : Parcelable {

    @Parcelize
    data class Temperature(
        val day: Double,
        val eve: Double,
        val max: Double,
        val min: Double,
        val morn: Double,
        val night: Double
    ) : Parcelable

    @Parcelize
    data class FeelsLike(val day: Double, val eve: Double, val morn: Double, val night: Double) :
        Parcelable
}
