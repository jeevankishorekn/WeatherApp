package com.jeevan.weatherapp.ui.weather

sealed interface WeatherIntent {
    data class SearchCity(val city: String) : WeatherIntent
    data object Refresh : WeatherIntent
    data object DismissError : WeatherIntent
}

