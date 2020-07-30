package com.hatem.noureddine.weatherapp.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.model.Place
import com.hatem.noureddine.core.domain.exceptions.GenericException
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.weatherapp.SampleApplication

class LocationsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val weatherSDK
        get() = this.getApplication<SampleApplication>().weatherSDK

    fun getLocations(): LiveData<out Resource<List<Location>>> =
        weatherSDK.getLocations()

    fun insertLocation(place: Place): LiveData<out Resource<Location>> {
        return if (place.name?.isNotBlank() == true && place.latLng != null) {
            weatherSDK.insertLocation(
                Location(
                    name = place.name!!,
                    longitude = place.latLng!!.longitude,
                    latitude = place.latLng!!.latitude
                )
            )
        } else
            MutableLiveData<Resource<Location>>(Resource.Error(GenericException("Place name, latitude, longitude are mandatory")))
    }
}