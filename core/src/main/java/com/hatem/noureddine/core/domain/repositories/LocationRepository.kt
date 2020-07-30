package com.hatem.noureddine.core.domain.repositories

import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource

interface LocationRepository {

    fun getLocations(): LiveData<out Resource<List<Location>>>

    fun insertLocation(location: Location): LiveData<out Resource<Location>>
}