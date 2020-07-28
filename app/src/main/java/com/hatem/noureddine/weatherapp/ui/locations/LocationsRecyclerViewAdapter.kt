package com.hatem.noureddine.weatherapp.ui.locations

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.weatherapp.R
import com.hatem.noureddine.weatherapp.utils.inflateItem

class LocationsRecyclerViewAdapter(private val values: List<Location>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CITY_VIEW -> CityViewHolder(parent inflateItem R.layout.fragment_locations_item)
            EMPTY_VIEW -> EmptyViewHolder(parent inflateItem R.layout.fragment_locations_empty)
            else -> throw IllegalArgumentException("Sorry this view type ($viewType) is not treated")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && values.isEmpty()) {
            EMPTY_VIEW
        } else {
            CITY_VIEW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CityViewHolder) {
            holder.onBind(values[position])
        }
    }

    override fun getItemCount(): Int = when (values.size) {
        0 -> 1
        else -> values.size
    }

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cityNameView: TextView = view.findViewById(R.id.city_name)

        fun onBind(location: Location) {
            cityNameView.text = location.name
        }
    }


    inner class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val CITY_VIEW = 0
        private const val EMPTY_VIEW = 1
    }
}