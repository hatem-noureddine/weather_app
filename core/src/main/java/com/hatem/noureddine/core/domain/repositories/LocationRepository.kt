package com.hatem.noureddine.core.domain.repositories

import androidx.lifecycle.LiveData
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import kotlin.coroutines.CoroutineContext

interface LocationRepository {

    fun getLocations(coroutineContext: CoroutineContext): LiveData<out Resource<List<Location>>>

    fun insertLocation(
        coroutineContext: CoroutineContext,
        location: Location
    ): LiveData<out Resource<Location>>
}