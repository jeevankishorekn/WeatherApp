package com.jeevan.weatherapp.ui.weather

import com.jeevan.weatherapp.domain.model.DayForecast

data class WeatherState(
    val city: String = "",
    val forecasts: List<DayForecast> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOfflineData: Boolean = false,
    val isOnline: Boolean = true
)

