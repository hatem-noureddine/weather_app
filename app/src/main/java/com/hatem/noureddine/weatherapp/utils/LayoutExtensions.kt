package com.hatem.noureddine.weatherapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

infix fun ViewGroup.inflateItem(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

infix fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this)
}


fun ViewGroup?.inflateFragment(inflater: LayoutInflater, @LayoutRes layout: Int): View {
    return inflater.inflate(layout, this, false)
}