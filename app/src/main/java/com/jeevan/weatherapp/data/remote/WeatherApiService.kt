package com.jeevan.weatherapp.data.remote

import com.google.gson.annotations.SerializedName
import com.jeevan.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Open-Meteo Forecast API – returns daily forecast data.
 * Docs: https://open-meteo.com/en/docs
 */
interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weather_code",
        @Query("forecast_days") forecastDays: Int = 3,
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}


