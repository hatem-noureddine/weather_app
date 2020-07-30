package com.hatem.noureddine.weatherapp.ui.locations

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
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
import com.hatem.noureddine.weatherapp.ui.weathers.WeatherActivity
import com.hatem.noureddine.weatherapp.utils.SpaceItemDecoration
import com.hatem.noureddine.weatherapp.utils.dp
import kotlinx.android.synthetic.main.activity_locations_list.*

/**
 * Activity to show locations
 * @property viewModel LocationsViewModel
 * @property startForResult ActivityResultLauncher<(android.content.Intent..android.content.Intent?)>
 */
class LocationsActivity : AppCompatActivity(), Observer<Resource<List<Location>>> {

    private val viewModel: LocationsViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    result.data?.let { data ->
                        val place = Autocomplete.getPlaceFromIntent(data)
                        viewModel.insertLocation(place).observe(this, Observer {})
                    }
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    result.data?.let { data ->
                        val status = Autocomplete.getStatusFromIntent(data)
                        // TODO try to resolve error
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list)
        viewModel.getLocations().observe(this, this)
        initFaButton()

        //Initialize Places
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.PLACES_API_KEY)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val space10 = 10.dp
        val footerSpace = 30.dp
        recyclerView.addItemDecoration(
            SpaceItemDecoration(
                space10,
                space10,
                space10,
                space10,
                footerSpace
            )
        )

        val onClickAction = MutableLiveData<Location>().apply {
            observe(this@LocationsActivity, Observer {
                // open details weather
                startActivity(WeatherActivity.newInstance(this@LocationsActivity, it))
            })
        }

        recyclerView.adapter = LocationsRecyclerViewAdapter(onClickAction)
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

    /**
     * Called when the data is changed.
     * @param t  The new data
     */
    override fun onChanged(resource: Resource<List<Location>>?) {
        val adapter = (recyclerView.adapter as? LocationsRecyclerViewAdapter)
        when (resource) {
            is Resource.Error -> adapter?.values = emptyList()
            is Resource.Success -> adapter?.values = resource.data
        }
    }
}