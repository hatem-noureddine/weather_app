package com.hatem.noureddine.core.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class Location(
    val id: Int = 0,
    val longitude: Double,
    val latitude: Double,
    val name: String
) : Parcelable