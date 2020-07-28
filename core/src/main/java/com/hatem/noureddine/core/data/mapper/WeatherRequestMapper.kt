package com.hatem.noureddine.core.data.mapper

import com.hatem.noureddine.core.data.remote.models.OpenWeatherMapRequest

/**
 * Convert Weather request object to query map
 * @receiver OpenWeatherMapRequest
 * @return Map<String, String>
 */
internal fun OpenWeatherMapRequest.toQueryMap(): Map<String, String> {
    return hashMapOf<String, String>().apply {
        put("lat", "$latitude")
        put("lon", "$longitude")
        put("exclude", exclude.joinToString(",", transform = { it.value }))
        toMap()
    }
}
