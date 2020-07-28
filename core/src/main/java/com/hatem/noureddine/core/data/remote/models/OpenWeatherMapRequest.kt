package com.hatem.noureddine.core.data.remote.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

/**
 * Weather request params
 * @property latitude Double
 * @property longitude Double
 * @property exclude List<Exclude>
 * @constructor
 */
@Parcelize
@Keep
internal data class OpenWeatherMapRequest(
    val latitude: Double,
    val longitude: Double,
    val exclude: List<Exclude> = listOf(Exclude.HOURLY, Exclude.MINUTELY, Exclude.CURRENT)
) : Parcelable

/**
 * Data to exclude from network response
 * @constructor
 */
internal enum class Exclude(val value: String) {
    HOURLY("hourly"),
    MINUTELY("minutely"),
    DAILY("daily"),
    CURRENT("current")
}