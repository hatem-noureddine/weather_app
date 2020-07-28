package com.hatem.noureddine.weatherapp.ui.locations

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.libraries.places.api.model.Place
import com.hatem.noureddine.core.domain.WeatherSDK
import com.hatem.noureddine.core.domain.exceptions.GenericException
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource

class LocationsViewModel @ViewModelInject constructor(private val weatherSDK: WeatherSDK) :
    ViewModel() {

    fun getLocations(): LiveData<out Resource<List<Location>>> =
        weatherSDK.LocationRepository.getLocations()

    fun insertLocation(place: Place): LiveData<out Resource<Location>> {
        return if (place.name?.isNotBlank() == true && place.latLng != null) {
            weatherSDK.LocationRepository.insertLocation(
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