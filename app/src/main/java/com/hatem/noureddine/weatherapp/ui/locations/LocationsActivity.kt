package com.hatem.noureddine.weatherapp.ui.locations

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.weatherapp.BuildConfig
import com.hatem.noureddine.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_locations_list.*


@AndroidEntryPoint
class LocationsActivity : AppCompatActivity() {

    private val viewModel: LocationsViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    result.data?.let { data ->
                        val place = Autocomplete.getPlaceFromIntent(data)
                        viewModel.insertLocation(place)
                    }
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    result.data?.let { data ->
                        val status = Autocomplete.getStatusFromIntent(data)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list)
        initFaButton()

        //Initialize Places
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.PLACES_API_KEY)
        }

        refreshCountries()
    }

    private fun refreshCountries() {
        viewModel.getLocations().observe(this, Observer<Resource<List<Location>>> {
            if (it is Resource.Error) {
                it.throwable.printStackTrace()
                recyclerView.adapter = LocationsRecyclerViewAdapter(emptyList())
                //Snackbar.make()
            }

            if (it is Resource.Success) {
                recyclerView.adapter = LocationsRecyclerViewAdapter(it.data)
            }
        })
    }

    private fun initFaButton() {
        fab.setOnClickListener {
            // Set the fields to specify which types of place data to return.
            val fields: List<Place.Field> = listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )

            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
            startForResult.launch(intent)
        }
    }

    override fun onDestroy() {
        if (Places.isInitialized()) {
            Places.deinitialize()
        }
        super.onDestroy()
    }
}