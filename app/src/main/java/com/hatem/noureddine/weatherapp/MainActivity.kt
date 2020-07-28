package com.hatem.noureddine.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import com.google.android.libraries.places.api.Places
import com.hatem.noureddine.core.domain.WeatherSDK
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherSDK: WeatherSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyDbKdC5l9gpSkmXCHARtAseO_orTI4z-X8")
        }

        weatherSDK.LocationRepository.insertLocation(
            lifecycle.coroutineScope.coroutineContext,
            Location(
                longitude = 10.634584,
                latitude = 35.8245029,
                name = "TestLocation"
            )
        ).observe(this, Observer {
            if (it is Resource.Error) {
                it.throwable.printStackTrace()
            }

            if (it is Resource.Success) {
                Log.e("TAG", "PutLocations: ${it.data}")
            }
        })


        weatherSDK.LocationRepository.getLocations(lifecycle.coroutineScope.coroutineContext)
            .observe(this, Observer {
                if (it is Resource.Error) {
                    it.throwable.printStackTrace()
                }

                if (it is Resource.Success) {
                    Log.e("TAG", "GetLocations: ${it.data}")
                }
            })


        weatherSDK.weatherRepository.getWeathers(
            lifecycle.coroutineScope.coroutineContext,
            10.634584,
            35.8245029
        )
            .observe(this, Observer {
                if (it is Resource.Error) {
                    it.throwable.printStackTrace()
                }

                if (it is Resource.Success) {
                    Log.e("TAG", "GetWeathers: ${it.data}")
                }
            })
    }

    override fun onDestroy() {
        if (Places.isInitialized()) {
            Places.deinitialize()
        }
        super.onDestroy()
    }
}