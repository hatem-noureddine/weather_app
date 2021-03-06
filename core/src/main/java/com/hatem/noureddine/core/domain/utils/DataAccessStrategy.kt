package com.hatem.noureddine.core.domain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.hatem.noureddine.core.data.remote.models.Response
import com.hatem.noureddine.core.domain.exceptions.GenericException
import com.hatem.noureddine.core.domain.exceptions.NetworkException
import com.hatem.noureddine.core.domain.models.Resource
import kotlinx.coroutines.Dispatchers

internal fun <D, R> performGetOperation(
    databaseQuery: () -> LiveData<D>,
    networkCall: suspend () -> Response<R>,
    saveCallResult: suspend (R) -> Unit
): LiveData<out Resource<D>> =
    liveData(Dispatchers.IO) {
        val source = databaseQuery.invoke().map { Resource.Success(it) as Resource<D> }
        emitSource(source)

        when (val responseStatus = networkCall.invoke()) {
            is Response.Success<R> -> saveCallResult(responseStatus.data)
            is Response.Error.ErrorException -> {
                emit(Resource.Error(GenericException(responseStatus.message)))
                emitSource(source)
            }
            is Response.Error.NoNetworkException -> {
                emit(Resource.Error(NetworkException()))
            }
        }
    }