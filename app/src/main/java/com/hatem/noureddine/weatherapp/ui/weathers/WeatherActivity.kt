package com.hatem.noureddine.weatherapp.ui.weathers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.core.domain.models.Resource
import com.hatem.noureddine.weatherapp.R
import com.hatem.noureddine.weatherapp.utils.SpaceItemDecoration
import com.hatem.noureddine.weatherapp.utils.dp
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import com.zhpan.indicator.option.IndicatorOptions
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * activity to show weather data
 * @property viewModel WeathersViewModel
 * @property location Location?
 */
class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeathersViewModel by viewModels()

    private val location: Location?
        get() = intent.getParcelableExtra("location")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initRecyclerView()

        location?.let {
            viewModel.getWeathers(it).observe(this, Observer { resource ->
                if (resource is Resource.Success) {
                    weather_view_pager.adapter = WeatherAdapter(resource.data)
                }
            })
        }

        initIndicators()
    }

    private fun initRecyclerView() {
        val space10 = 10.dp
        weather_view_pager.addItemDecoration(
            SpaceItemDecoration(space10, space10, space10, space10)
        )
    }

    private fun initIndicators() {
        val indicatorOptions = IndicatorOptions().apply {
            setSliderColor(
                ContextCompat.getColor(this@WeatherActivity, R.color.colorPrimary),
                ContextCompat.getColor(this@WeatherActivity, R.color.colorAccent)
            )
        }
        indicator_view_pager
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setupWithViewPager(weather_view_pager)

        indicator_view_pager.setIndicatorOptions(indicatorOptions)
    }


    companion object {
        fun newInstance(context: Context, location: Location): Intent {
            return Intent(context, WeatherActivity::class.java).apply {
                putExtra("location", location)
            }
        }
    }
}