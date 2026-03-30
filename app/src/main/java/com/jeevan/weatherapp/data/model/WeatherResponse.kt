package com.jeevan.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val daily: DailyData?
)

data class DailyData(
    val time: List<String>,
    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>
)