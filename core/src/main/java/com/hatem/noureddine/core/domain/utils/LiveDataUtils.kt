package com.hatem.noureddine.core.domain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.hatem.noureddine.core.domain.models.Resource

fun <T> LiveData<List<T>>.notNull(): LiveData<List<T>> {
    return MediatorLiveData<List<T>>().apply {
        addSource(this@notNull) {
            when (it.isNullOrEmpty()) {
                true -> postValue(emptyList())
                else -> postValue(it)
            }
        }
        postValue(emptyList())
    }
}

/**
 * Helper to use Sealed Class with liveData and have realtime working observer
 * @receiver LiveData<List<D>>
 * @param loadingLiveData MutableLiveData<Loading<List<O>>>
 * @param transform [@kotlin.ExtensionFunctionType] Function1<D, O>
 * @return LiveData<Resource<List<O>>>
 */
fun <D, O> LiveData<List<D>>.transform(
    loadingLiveData: MutableLiveData<Resource.Loading<List<O>>>,
    transform: D.() -> O
): LiveData<Resource<List<O>>> {
    val mappedSource = map { list ->
        Resource.Success(list.map { transform(it) })
    }

    return MediatorLiveData<Resource<List<O>>>().apply {
        addSource(mappedSource) {
            postValue(it)
        }

        addSource(loadingLiveData) {
            postValue(it)
        }
    }
}