package com.hatem.noureddine.weatherapp

import android.content.Context
import com.hatem.noureddine.core.domain.WeatherSDK
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): WeatherSDK =
        WeatherSDK(context)
}