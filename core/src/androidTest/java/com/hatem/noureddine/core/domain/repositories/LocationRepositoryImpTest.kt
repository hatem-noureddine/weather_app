package com.hatem.noureddine.core.domain.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.data.local.models.DBLocation
import com.hatem.noureddine.core.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.internal.verification.Times

@RunWith(AndroidJUnit4ClassRunner::class)
class LocationRepositoryImpTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: LocationRepositoryImp
    private lateinit var mockLocationDao: LocationDao

    @Before
    fun init() {
        mockLocationDao = Mockito.mock(LocationDao::class.java)
        mockRepository = Mockito.spy(LocationRepositoryImp(mockLocationDao))
    }

    @Test
    fun getLocations() {
        Mockito.`when`(mockLocationDao.getLocations()).thenReturn(
            MutableLiveData<List<DBLocation>>(
                listOf(DBLocation(1, 10.10, 11.0, "test"))
            )
        )

        val observer = Mockito.spy(Observer<Resource<*>> {})

        runBlocking(Dispatchers.Main) {
            mockRepository.getLocations().observeForever(observer)

            Mockito.verify(observer, Times(1))
                .onChanged(Mockito.isA(Resource.Loading::class.java))

            Mockito.verify(observer, Times(1))
                .onChanged(Mockito.isA(Resource.Success::class.java))

            Mockito.verify(observer, Times(1))
                .onChanged(Mockito.isA(Resource.Loading::class.java))

            Mockito.verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun insertLocation() {
    }
}
