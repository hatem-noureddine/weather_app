package com.hatem.noureddine.core.data.remote.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
internal data class FeelsLike(
    val day: Double, // 300.83
    val eve: Double, // 300.83
    val morn: Double, // 300.83
    val night: Double // 300.82
) : Parcelable