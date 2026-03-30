package com.jeevan.weatherapp.ui.weather

import android.content.Context
import com.jeevan.weatherapp.R
import com.jeevan.weatherapp.domain.model.CityNotFoundException
import com.jeevan.weatherapp.domain.model.NoForecastDataException

class WeatherViewMapper(private val context: Context) {

    fun emptyCity(): String =
        context.getString(R.string.error_empty_city)

    fun errorMessage(error: Throwable): String = when (error) {
        is CityNotFoundException -> context.getString(R.string.error_city_not_found, error.cityName)
        is NoForecastDataException -> context.getString(R.string.error_no_forecast_data)
        else -> context.getString(R.string.error_no_offline_data)
    }
}

