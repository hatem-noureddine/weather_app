package com.hatem.noureddine.core.data.remote.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 *
 * @property daily List<Daily>
 * @property lat Double
 * @property lon Double
 * @property timezone String
 * @property timezoneOffset Int
 * @constructor
 */
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
internal data class OpenWeatherMapResponse(
    val daily: List<Daily>,
    val lat: Double, // 33.44
    val lon: Double, // -94.04
    val timezone: String, // America/Chicago
    @Json(name = "timezone_offset")
    val timezoneOffset: Int // -18000
) : Parcelable
