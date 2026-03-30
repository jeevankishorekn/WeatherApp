package com.jeevan.weatherapp.domain.model

import androidx.annotation.StringRes
import com.jeevan.weatherapp.R

data class DayForecast(
    val date: String,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val weatherCode: Int
) {
    @get:StringRes
    val weatherConditionRes: Int
        get() = mapWeatherCode(weatherCode)

    val weatherIcon: Int
        get() = mapWeatherIcon(weatherCode)
}

/**
 * Maps WMO weather interpretation codes to string resource IDs.
 * See https://open-meteo.com/en/docs#weathervariables
 */
@StringRes
private fun mapWeatherCode(code: Int): Int = when (code) {
    0 -> R.string.weather_clear_sky
    1 -> R.string.weather_mainly_clear
    2 -> R.string.weather_partly_cloudy
    3 -> R.string.weather_overcast
    45, 48 -> R.string.weather_foggy
    51, 53, 55 -> R.string.weather_drizzle
    56, 57 -> R.string.weather_freezing_drizzle
    61, 63, 65 -> R.string.weather_rainy
    66, 67 -> R.string.weather_freezing_rain
    71, 73, 75 -> R.string.weather_snowy
    77 -> R.string.weather_snow_grains
    80, 81, 82 -> R.string.weather_rain_showers
    85, 86 -> R.string.weather_snow_showers
    95 -> R.string.weather_thunderstorm
    96, 99 -> R.string.weather_thunderstorm_hail
    else -> R.string.weather_unknown
}

private fun mapWeatherIcon(code: Int): Int = when (code) {
    0 -> R.drawable.ic_sunny
    1, 2 -> R.drawable.ic_partly_cloudy
    3 -> R.drawable.ic_cloudy
    45, 48 -> R.drawable.ic_foggy
    51, 53, 55, 56, 57 -> R.drawable.ic_drizzle
    61, 63, 65, 66, 67, 80, 81, 82 -> R.drawable.ic_rainy
    71, 73, 75, 77, 85, 86  -> R.drawable.ic_snow
    95 -> R.drawable.ic_thunder_storm
    96, 99 -> R.drawable.ic_hail
    else -> R.drawable.ic_default
}

