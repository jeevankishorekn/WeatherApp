package com.jeevan.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val city: String,
    val date: String,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val weatherCode: Int
)