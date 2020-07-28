package com.hatem.noureddine.core.domain.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<List<T>>.empty(): LiveData<List<T>> {
    return MediatorLiveData<List<T>>().apply {
        postValue(emptyList())
        addSource(this@empty) {
            when (it.isNullOrEmpty()) {
                true -> postValue(emptyList())
                else -> postValue(it)
            }
        }
    }
}

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this) { it?.let { mediator.postValue(it) } }
    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer { it?.let(observer) })
}

class NonNullMediatorLiveData<T> : MediatorLiveData<T>()
