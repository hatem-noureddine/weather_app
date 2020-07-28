package com.hatem.noureddine.core.data.local.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hatem.noureddine.core.data.local.LOCATION_DB_TABLE_NAME

/**
 * Location database model
 * @property uid Int: unique ID
 * @property longitude Double
 * @property latitude Double
 * @property name String: city name
 * @constructor
 */
@Keep
@Entity(tableName = LOCATION_DB_TABLE_NAME)
internal data class DBLocation(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val longitude: Double,
    val latitude: Double,
    val name: String
)