package com.hatem.noureddine.core.domain.repositories

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.hatem.noureddine.core.data.local.dao.LocationDao
import com.hatem.noureddine.core.domain.mapper.toDBLocation
import com.hatem.noureddine.core.domain.mapper.toLocation
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.core.domain.utils.notNull
import com.hatem.noureddine.core.domain.utils.transform
import kotlinx.coroutines.Dispatchers

internal open class LocationRepositoryImp constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val localDataSource: LocationDao
) : LocationRepository {

    override fun getLocations(): LiveData<out Resource<List<Location>>> {
        return liveData(Dispatchers.IO) {
            val loadingLiveData = MutableLiveData<Resource.Loading<List<Location>>>()
            val source =
                localDataSource.getLocations().notNull().transform(loadingLiveData) { toLocation() }

            loadingLiveData.postValue(Resource.Loading(true))
            emitSource(source)
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