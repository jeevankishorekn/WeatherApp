package com.jeevan.weatherapp.domain.model

/**
 * Thrown when the weather API returns no daily forecast data.
 */
class NoForecastDataException : Exception("No forecast data available")

