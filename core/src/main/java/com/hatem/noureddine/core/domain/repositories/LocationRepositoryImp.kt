package com.hatem.noureddine.core.domain.repositories

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.domain.mapper.toDBLocation
import com.hatem.noureddine.core.domain.mapper.toLocation
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal open class LocationRepositoryImp constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val localDataSource: LocationDao
) : LocationRepository {

    override fun getLocations(coroutineContext: CoroutineContext): LiveData<out Resource<List<Location>>> {
        return flow {
            emit(Resource.Loading(true))

            val source = localDataSource.getLocations().distinctUntilChanged().map { list ->
                list.map { location ->
                    location.toLocation()
                }
            }.map { Resource.Success(it) as Resource<List<Location>> }

            emitAll(source)
            emit(Resource.Loading(false))
        }.asLiveData(coroutineContext)
    }

    override fun insertLocation(
        coroutineContext: CoroutineContext,
        location: Location
    ): LiveData<out Resource<Location>> {
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