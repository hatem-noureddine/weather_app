package com.hatem.noureddine.core.data.remote.interceptors

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class WeatherAppIdInterceptorTest {

    @Test
    fun intercept() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())
        val apiKey = "test-api-key"
        val okHttpClient: OkHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(WeatherAppIdInterceptor(apiKey))
            .build()

        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        val request = mockWebServer.takeRequest()
        Assert.assertEquals("/?appid=$apiKey", request.path)
        mockWebServer.shutdown()
    }
}