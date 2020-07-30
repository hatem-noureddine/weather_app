package com.hatem.noureddine.weatherapp.ui.locations

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.hatem.noureddine.core.domain.models.Location
import com.hatem.noureddine.weatherapp.R
import com.hatem.noureddine.weatherapp.utils.inflateItem

class LocationsRecyclerViewAdapter(private val onClickAction: MutableLiveData<Location>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var values: List<Location> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CITY_VIEW -> CityViewHolder(parent inflateItem R.layout.activity_locations_item)
            EMPTY_VIEW -> EmptyViewHolder(parent inflateItem R.layout.activity_locations_empty)
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
            holder.onBind(values[position], onClickAction)
        }
    }

    override fun getItemCount(): Int = when (values.size) {
        0 -> 1
        else -> values.size
    }

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cityNameView: TextView = view.findViewById(R.id.city_name)

        init {

        }

        fun onBind(location: Location, onClickAction: MutableLiveData<Location>) {
            cityNameView.text = location.name
            itemView.setOnClickListener { onClickAction.value = location }
        }
    }


    inner class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val CITY_VIEW = 0
        private const val EMPTY_VIEW = 1
    }
}