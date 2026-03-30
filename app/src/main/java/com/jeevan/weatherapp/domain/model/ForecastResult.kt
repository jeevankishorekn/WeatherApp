package com.jeevan.weatherapp.domain.model

/**
 * Wraps forecast data with its source information.
 * The repository determines whether data came from network or local cache.
 */
data class ForecastResult(
    val forecasts: List<DayForecast>,
    val isOffline: Boolean
)

