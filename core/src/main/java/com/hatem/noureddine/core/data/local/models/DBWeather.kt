package com.hatem.noureddine.core.data.local.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import com.hatem.noureddine.core.data.local.WEATHER_DB_TABLE_NAME

/**
 * Weather database model
 * @property locationUid Int
 * @property timestamp Long : pop date
 * @property sunrise Long: sunrise time
 * @property sunset Long: sunset time
 * @property temp Temperature
 * @property humidity Int
 * @property feelsLike FeelsLike
 * @property windSpeed Double
 * @property windDeg Int : wind direction
 * @property clouds Int
 * @property dewPoint Double
 * @property pop Double
 * @property pressure Int
 * @property rain Double?
 * @property uvi Double
 * @property description String
 * @property icon String
 * @constructor
 */
@Keep
@Entity(
    tableName = WEATHER_DB_TABLE_NAME,
    primaryKeys = ["location_uid", "timestamp"],
    foreignKeys = [ForeignKey(
        entity = DBLocation::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("location_uid"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class DBWeather(
    @ColumnInfo(name = "location_uid") val locationUid: Int,
    val timestamp: Long,
    val sunrise: Long,
    val sunset: Long,
    @Embedded val temp: Temperature,
    val humidity: Int,
    @Embedded val feelsLike: FeelsLike,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "wind_deg") val windDeg: Int,
    val clouds: Int,
    @ColumnInfo(name = "dew_point") val dewPoint: Double,
    val pop: Double,
    val pressure: Int,
    val rain: Double?,
    val uvi: Double,
    val description: String,
    val icon: String
)

/**
 * Weather temperature model
 * @property day Double
 * @property eve Double
 * @property max Double
 * @property min Double
 * @property morn Double
 * @property night Double
 * @constructor
 */
internal data class Temperature(
    @ColumnInfo(name = "temp_day") val day: Double,
    @ColumnInfo(name = "temp_eve") val eve: Double,
    @ColumnInfo(name = "temp_max") val max: Double,
    @ColumnInfo(name = "temp_min") val min: Double,
    @ColumnInfo(name = "temp_morn") val morn: Double,
    @ColumnInfo(name = "temp_night") val night: Double
)

/**
 * Weather feels model
 * @property day Double
 * @property eve Double
 * @property morn Double
 * @property night Double
 * @constructor
 */
internal data class FeelsLike(
    @ColumnInfo(name = "Feels_day") val day: Double, // 300.83
    @ColumnInfo(name = "Feels_eve") val eve: Double, // 300.83
    @ColumnInfo(name = "Feels_morn") val morn: Double, // 300.83
    @ColumnInfo(name = "Feels_night") val night: Double // 300.82
)
