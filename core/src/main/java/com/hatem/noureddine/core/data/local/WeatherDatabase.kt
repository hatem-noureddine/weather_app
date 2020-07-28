package com.hatem.noureddine.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.data.local.dao.WeatherDao
import com.hatem.noureddine.core.data.local.models.DBLocation
import com.hatem.noureddine.core.data.local.models.DBWeather

/**
 * Database class using Room
 * In our data base we have 2 tables one for location and one for weathers
 */
@Database(entities = [DBLocation::class, DBWeather::class], version = 1, exportSchema = false)
internal abstract class WeatherDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, WEATHER_DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}