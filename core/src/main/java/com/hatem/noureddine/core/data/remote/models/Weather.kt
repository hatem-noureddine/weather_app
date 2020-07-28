package com.hatem.noureddine.core.data.remote.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
internal data class Weather(
    val description: String, // light rain
    val icon: String, // 10d
    val id: Int, // 500
    val main: String // Rain
) : Parcelable
