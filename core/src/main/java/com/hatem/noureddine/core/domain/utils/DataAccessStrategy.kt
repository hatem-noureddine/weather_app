package com.hatem.noureddine.core.domain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.hatem.noureddine.core.data.remote.models.Response
import com.hatem.noureddine.core.domain.exceptions.GenericException
import com.hatem.noureddine.core.domain.exceptions.NetworkException
import com.hatem.noureddine.core.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal fun <D, R> performGetOperation(
    coroutineContext: CoroutineContext,
    databaseQuery: () -> Flow<D>,
    networkCall: suspend () -> Response<R>,
    saveCallResult: suspend (R) -> Unit
): LiveData<out Resource<D>> =
    flow {
        emit(Resource.Loading(true))
        val source = databaseQuery.invoke().map { Resource.Success(it) as Resource<D> }
        emitAll(source)

        when (val responseStatus = networkCall.invoke()) {
            is Response.Success<R> -> saveCallResult(responseStatus.data)
            is Response.Error.ErrorException -> {
                emit(Resource.Error(GenericException(responseStatus.message)))
                emitAll(source)
            }
            is Response.Error.NoNetworkException -> {
                emit(Resource.Error(NetworkException()))
            }
        }
        emit(Resource.Loading(false))
    }.asLiveData(coroutineContext)


internal fun <D, R> performNetworkGetOperation(
    coroutineContext: CoroutineContext,
    networkCall: suspend () -> Response<R>,
    mapResult: suspend (R) -> D
): LiveData<out Resource<D>> =
    flow {
        emit(Resource.Loading(true))

        when (val responseStatus = networkCall.invoke()) {
            is Response.Success<R> -> emit(Resource.Success(mapResult(responseStatus.data)))
            is Response.Error.ErrorException -> {
                emit(Resource.Error(GenericException(responseStatus.message)))
            }
            is Response.Error.NoNetworkException -> {
                emit(Resource.Error(NetworkException()))
            }
        }
        emit(Resource.Loading(false))
    }.asLiveData(coroutineContext)