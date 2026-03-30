package com.jeevan.weatherapp.domain.repository

import com.jeevan.weatherapp.domain.model.ForecastResult

interface WeatherRepository {

    /**
     * Fetches 3-day forecast for the given city.
     * Tries the remote API first and caches the result locally.
     * On network failure, falls back to locally cached data.
     * The returned [ForecastResult] indicates whether the data is offline.
     */
    suspend fun getForecast(city: String): Result<ForecastResult>
}
