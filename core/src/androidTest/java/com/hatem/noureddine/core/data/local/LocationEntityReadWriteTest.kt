package com.hatem.noureddine.core.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.data.local.models.DBLocation
import com.hatem.noureddine.core.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class LocationEntityReadWriteTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var locationDao: LocationDao
    private lateinit var db: WeatherDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WeatherDatabase::class.java
        ).build()
        locationDao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertNewLocation() {
        val location = DBLocation(longitude = 10.10, latitude = 12.12, name = "test-db")
        runBlocking {
            locationDao.insert(location)
            val locations = locationDao.getLocations().getOrAwaitValue()
            Assert.assertEquals(locations.isNotEmpty(), true)
            Assert.assertEquals(locations.size, 1)
        }
    }


    @Test
    @Throws(Exception::class)
    fun getLocations() {
        val locations = listOf(
            DBLocation(longitude = 10.10, latitude = 20.20, name = "test-db-1"),
            DBLocation(longitude = 11.11, latitude = 21.21, name = "test-db-2"),
            DBLocation(longitude = 12.12, latitude = 22.22, name = "test-db-3")
        )

        runBlocking {
            locationDao.deleteAll()

            locations.forEach {
                locationDao.insert(it)
            }

            val locationsDB = locationDao.getLocations().getOrAwaitValue()
            Assert.assertEquals(locationsDB.isNotEmpty(), true)
            Assert.assertEquals(locationsDB.size, 3)
            locationsDB.forEachIndexed { index, item ->
                val location = locations[index]
                Assert.assertEquals(item.uid != 0, true)
                Assert.assertEquals(location.latitude, item.latitude, 0.0)
                Assert.assertEquals(location.longitude, item.longitude, 0.0)
                Assert.assertEquals(location.name, item.name)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteLocations() {
        val locations = listOf(
            DBLocation(longitude = 10.10, latitude = 20.20, name = "test-db-1"),
            DBLocation(longitude = 11.11, latitude = 21.21, name = "test-db-2"),
            DBLocation(longitude = 12.12, latitude = 22.22, name = "test-db-3")
        )

        runBlocking {
            locationDao.deleteAll()

            locations.forEach {
                locationDao.insert(it)
            }

            val locationsDB = locationDao.getLocations().getOrAwaitValue()
            Assert.assertEquals(locationsDB.isNotEmpty(), true)
            locationDao.deleteAll()
            val locationsAfterDelete = locationDao.getLocations().getOrAwaitValue()
            Assert.assertEquals(locationsAfterDelete.isEmpty(), true)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteLocation() {
        val item = DBLocation(longitude = 11.11, latitude = 21.21, name = "test-db-2")
        val locations = listOf(
            DBLocation(longitude = 10.10, latitude = 20.20, name = "test-db-1"),
            item,
            DBLocation(longitude = 12.12, latitude = 22.22, name = "test-db-3")
        )

        runBlocking {
            locationDao.deleteAll()

            locations.forEach {
                locationDao.insert(it)
            }

            val locationsDB = locationDao.getLocations().getOrAwaitValue()
            Assert.assertEquals(locationsDB.isNotEmpty(), true)
            Assert.assertEquals(locationsDB.size, 3)

            locationDao.delete(item)
            val locationsDel = locations.toMutableList()
            locationsDel.remove(item)

            val locationsAfterDelete = locationDao.getLocations().getOrAwaitValue()
            Assert.assertEquals(locationsAfterDelete.isNotEmpty(), true)
            Assert.assertEquals(locationsAfterDelete.size, 2)


            locationsAfterDelete.forEachIndexed { index, item ->
                val location = locationsDel[index]
                Assert.assertEquals(location.latitude, item.latitude, 0.0)
                Assert.assertEquals(location.longitude, item.longitude, 0.0)
                Assert.assertEquals(location.name, item.name)
            }
        }
    }
}