package com.hatem.noureddine.core.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.data.local.dao.WeatherDao
import com.hatem.noureddine.core.data.local.models.DBLocation
import com.hatem.noureddine.core.data.local.models.DBWeather
import com.hatem.noureddine.core.data.local.models.FeelsLike
import com.hatem.noureddine.core.data.local.models.Temperature
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
import kotlin.random.Random

@RunWith(AndroidJUnit4ClassRunner::class)
class WeatherEntityReadWriteTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var weatherDao: WeatherDao
    private lateinit var locationDao: LocationDao
    private lateinit var db: WeatherDatabase
    private var timeStamp = 123456789L

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WeatherDatabase::class.java
        ).build()
        weatherDao = db.weatherDao()
        locationDao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertNewWeather() {
        runBlocking {
            locationDao.deleteAll()
            weatherDao.deleteAll()
            val location = DBLocation(longitude = 10.10, latitude = 12.12, name = "test-db-1")
            val location2 = DBLocation(longitude = 11.11, latitude = 13.13, name = "test-db-2")
            locationDao.insert(location)
            locationDao.insert(location2)

            weatherDao.insert(randomWeather(1))
            weatherDao.insert(randomWeather(2))
            weatherDao.insert(randomWeather(1))
            val weathers = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathers.isNotEmpty(), true)
            Assert.assertEquals(weathers.size, 2)
        }
    }


    @Test
    @Throws(Exception::class)
    fun getWeathers() {
        val weathers = listOf(
            randomWeather(1, "test-1"),
            randomWeather(2),
            randomWeather(1, "test-2"),
            randomWeather(1, "test-3"),
            randomWeather(1, "test-4"),
            randomWeather(1, "test-5")
        )

        runBlocking {
            weatherDao.deleteAll()
            locationDao.deleteAll()
            val location = DBLocation(longitude = 10.10, latitude = 12.12, name = "test-db-1")
            val location2 = DBLocation(longitude = 11.11, latitude = 13.13, name = "test-db-2")
            locationDao.insert(location)
            locationDao.insert(location2)


            weathers.forEach {
                weatherDao.insert(it)
            }

            val weathersDB = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathersDB.isNotEmpty(), true)
            Assert.assertEquals(weathersDB.size, 5)

            val newWeathers = weathers.toMutableList()
            newWeathers.removeAt(1)
            weathersDB.forEachIndexed { index, item ->
                val weather = newWeathers[index]
                Assert.assertEquals(weather.description, item.description)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteWeathers() {
        val weathers = listOf(
            randomWeather(1, "test-1"),
            randomWeather(2),
            randomWeather(1, "test-2"),
            randomWeather(1, "test-3"),
            randomWeather(1, "test-4"),
            randomWeather(1, "test-5")
        )

        runBlocking {
            weatherDao.deleteAll()
            locationDao.deleteAll()
            val location = DBLocation(longitude = 10.10, latitude = 12.12, name = "test-db-1")
            val location2 = DBLocation(longitude = 11.11, latitude = 13.13, name = "test-db-2")
            locationDao.insert(location)
            locationDao.insert(location2)

            weathers.forEach {
                weatherDao.insert(it)
            }

            val weathersDB = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathersDB.isNotEmpty(), true)
            weatherDao.deleteAll()

            val weathersAfterDelete = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathersAfterDelete.isEmpty(), true)

            val weathers2AfterDelete = weatherDao.getWeathers(2).getOrAwaitValue()
            Assert.assertEquals(weathers2AfterDelete.isEmpty(), true)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteWeathersByLocationUniqueID() {
        val weathers = listOf(
            randomWeather(1, "test-1"),
            randomWeather(2),
            randomWeather(1, "test-2"),
            randomWeather(1, "test-3"),
            randomWeather(1, "test-4"),
            randomWeather(1, "test-5")
        )

        runBlocking {
            weatherDao.deleteAll()
            locationDao.deleteAll()
            val location = DBLocation(longitude = 10.10, latitude = 12.12, name = "test-db-1")
            val location2 = DBLocation(longitude = 11.11, latitude = 13.13, name = "test-db-2")
            locationDao.insert(location)
            locationDao.insert(location2)

            weathers.forEach {
                weatherDao.insert(it)
            }

            val weathersDB = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathersDB.isNotEmpty(), true)
            weatherDao.deleteAll(1)

            val weathersAfterDelete = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathersAfterDelete.isEmpty(), true)

            val weathers2AfterDelete = weatherDao.getWeathers(2).getOrAwaitValue()
            Assert.assertEquals(weathers2AfterDelete.isNotEmpty(), true)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteWeather() {
        val weathers = listOf(
            randomWeather(1, "test-1"),
            randomWeather(2),
            randomWeather(1, "test-2"),
            randomWeather(1, "test-3"),
            randomWeather(1, "test-4"),
            randomWeather(1, "test-5")
        )

        runBlocking {
            weatherDao.deleteAll()
            locationDao.deleteAll()
            val location = DBLocation(longitude = 10.10, latitude = 12.12, name = "test-db-1")
            val location2 = DBLocation(longitude = 11.11, latitude = 13.13, name = "test-db-2")
            locationDao.insert(location)
            locationDao.insert(location2)

            weathers.forEach {
                weatherDao.insert(it)
            }

            val weathersDB = weatherDao.getWeathers(2).getOrAwaitValue()
            Assert.assertEquals(weathersDB.isNotEmpty(), true)
            Assert.assertEquals(weathersDB.size, 1)

            weatherDao.delete(weathers[1])
            val weathersDel = weathers.toMutableList()
            weathersDel.removeAt(1)

            var weathersAfterDelete = weatherDao.getWeathers(2).getOrAwaitValue()
            Assert.assertEquals(weathersAfterDelete.isEmpty(), true)
            Assert.assertEquals(weathersAfterDelete.size, 0)

            weatherDao.delete(weathers.first())
            weathersDel.removeAt(0)

            weathersAfterDelete = weatherDao.getWeathers(1).getOrAwaitValue()
            Assert.assertEquals(weathersAfterDelete.isNotEmpty(), true)
            Assert.assertEquals(weathersAfterDelete.size, 4)


            weathersAfterDelete.forEachIndexed { index, item ->
                val weather = weathersDel[index]
                Assert.assertEquals(weather.description, item.description)
            }
        }
    }

    private fun randomWeather(locationUid: Int, desc: String = "test test test"): DBWeather {
        return DBWeather(
            locationUid,
            timeStamp(),
            Random.nextLong(),
            Random.nextLong(),
            Temperature(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextInt(),
            FeelsLike(
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble(),
                Random.nextDouble()
            ),
            Random.nextDouble(),
            Random.nextInt(),
            Random.nextInt(),
            Random.nextDouble(),
            Random.nextDouble(),
            Random.nextInt(),
            null,
            Random.nextDouble(),
            desc,
            "icon_rain"
        )
    }

    private fun timeStamp(): Long {
        timeStamp += 1L
        return timeStamp
    }
}