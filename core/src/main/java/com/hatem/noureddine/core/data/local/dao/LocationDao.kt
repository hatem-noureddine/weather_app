package com.hatem.noureddine.core.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hatem.noureddine.core.data.local.LOCATION_DB_TABLE_NAME
import com.hatem.noureddine.core.data.local.models.DBLocation

/**
 * Location DAO
 */
@Dao
internal interface LocationDao {
    /**
     * insert a location into database
     * @param location DBLocation
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: DBLocation)

    /**
     * fetch locations sort by name
     * @return LiveData<List<DBLocation>>
     */
    @Query("SELECT * from $LOCATION_DB_TABLE_NAME ORDER BY name ASC")
    fun getLocations(): LiveData<List<DBLocation>>

    /**
     * delete all location from database
     */
    @Query("DELETE FROM $LOCATION_DB_TABLE_NAME")
    suspend fun deleteAll()

    /**
     * Delete location from database
     * @param location DBLocation
     */
    @Delete
    suspend fun delete(location: DBLocation)
}
