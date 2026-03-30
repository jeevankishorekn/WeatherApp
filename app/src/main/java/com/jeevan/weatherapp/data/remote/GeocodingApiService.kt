package com.jeevan.weatherapp.data.remote

import com.jeevan.weatherapp.data.model.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Open-Meteo Geocoding API to convert city name → latitude/longitude.
 * Docs: https://open-meteo.com/en/docs/geocoding-api
 */
interface GeocodingApiService {

    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): GeocodingResponse
}

