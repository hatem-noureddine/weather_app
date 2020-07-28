package com.hatem.noureddine.core.data.remote.models


internal sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    sealed class Error : Response<Nothing>() {
        object NoNetworkException : Error()
        data class ErrorException(val message: String) : Error()
    }
}