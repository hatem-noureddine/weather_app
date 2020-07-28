package com.hatem.noureddine.core.domain.repositories

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.domain.mapper.toDBLocation
import com.hatem.noureddine.core.domain.mapper.toLocation
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import kotlinx.coroutines.Dispatchers

internal open class LocationRepositoryImp constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val localDataSource: LocationDao
) : LocationRepository {

    override fun getLocations(): LiveData<out Resource<List<Location>>> {
        Log.e("TAG", "0")
        return liveData(Dispatchers.IO) {
            Log.e("TAG", "1")
            emit(Resource.Loading(true))
            Log.e("TAG", "2")
            val source = localDataSource.getLocations().map { list ->
                list.map { location ->
                    location.toLocation()
                }
            }.map { Resource.Success(it) as Resource<List<Location>> }
            Log.e("TAG", "3")
            emitSource(source)
            emit(Resource.Loading(false))
        }
    }

    override fun insertLocation(location: Location): LiveData<out Resource<Location>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading(true))
            try {
                localDataSource.insert(location.toDBLocation())
                emit(Resource.Success(location))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
            emit(Resource.Loading(false))
        }
    }
}