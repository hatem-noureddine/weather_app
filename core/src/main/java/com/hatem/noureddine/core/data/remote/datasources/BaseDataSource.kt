package com.hatem.noureddine.core.data.remote.datasources

import androidx.annotation.VisibleForTesting
import com.hatem.noureddine.core.data.remote.models.Response.Error
import com.hatem.noureddine.core.data.remote.models.Response.Success
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Base class data source that help to process result from network data source
 */
internal abstract class BaseDataSource {

    /**
     * treat network call
     * @param call SuspendFunction0<Response<T>>
     * @return com.hatem.noureddine.core.data.remote.models.Response<T>
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal suspend fun <T> getResult(call: suspend () -> Response<T>): com.hatem.noureddine.core.data.remote.models.Response<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Success(body)
            }
            return error("${response.code()} :: ${response.message()}")
        } catch (e: Exception) {
            val isGenericIOException = when (e) {
                is SocketTimeoutException -> true
                is JsonEncodingException -> true
                is IOException -> true
                is JsonDataException -> false
                else -> false
            }

            return if (!isGenericIOException) {
                Error.NoNetworkException
            } else {
                error(e.message ?: e.toString())
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun <T> error(message: String): com.hatem.noureddine.core.data.remote.models.Response<T> {
        return Error.ErrorException("Network call has failed for a following reason: $message")
    }

}
