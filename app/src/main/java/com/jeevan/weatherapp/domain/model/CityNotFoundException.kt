package com.jeevan.weatherapp.domain.model

/**
 * Thrown when the geocoding API finds no results for the entered city name.
 */
class CityNotFoundException(val cityName: String) : Exception("City not found: $cityName")

