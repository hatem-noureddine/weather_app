package com.hatem.noureddine.core.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Help to intercept OkHttp request and inject WeatherOpenMap APP_ID automatically on every request
 * @property weatherAppId String
 * @constructor
 */
internal class WeatherAppIdInterceptor(private val weatherAppId: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("appid", weatherAppId)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}