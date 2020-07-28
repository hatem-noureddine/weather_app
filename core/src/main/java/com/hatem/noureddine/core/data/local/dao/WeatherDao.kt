package com.hatem.noureddine.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hatem.noureddine.core.data.local.WEATHER_DB_TABLE_NAME
import com.hatem.noureddine.core.data.local.models.DBWeather
import kotlinx.coroutines.flow.Flow

/**
 * Weather DAO
 */
@Dao
internal interface WeatherDao {

    /**
     * insert a weather into database
     * @param weather DBWeather
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: DBWeather)

    /**
     * fetch weathers by location unique ID and sort by timestamp
     * @param locationUid Int
     * @return LiveData<List<DBWeather>>
     */
    @Query("SELECT * from $WEATHER_DB_TABLE_NAME WHERE location_uid = :locationUid ORDER BY timestamp ASC")
    fun getWeathers(locationUid: Int): Flow<List<DBWeather>>

    /**
     * delete all weathers from database
     */
    @Query("DELETE FROM $WEATHER_DB_TABLE_NAME")
    suspend fun deleteAll()

    /**
     * delete all weathers if that location unique id from database
     * @param locationUid Int
     */
    @Query("DELETE from $WEATHER_DB_TABLE_NAME WHERE location_uid = :locationUid")
    suspend fun deleteAll(locationUid: Int)

    /**
     * delete all weathers from database before defined date
     * @param today Long
     */
    @Query("DELETE FROM $WEATHER_DB_TABLE_NAME where timestamp < :today")
    suspend fun deleteBeforeToday(today: Long)

    /**
     * Delete weather from database
     * @param weather DBWeather
     */
    @Delete
    suspend fun delete(weather: DBWeather)
}
