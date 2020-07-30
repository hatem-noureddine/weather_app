package com.hatem.noureddine.core.domain.models

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val isLoading: Boolean) : Resource<T>()
    data class Error<out T>(val throwable: Throwable) : Resource<T>()
}

