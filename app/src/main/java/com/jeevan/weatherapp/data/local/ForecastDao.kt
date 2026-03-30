package com.jeevan.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeevan.weatherapp.data.model.ForecastEntity

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast WHERE city = :city ORDER BY date ASC")
    suspend fun getForecastByCity(city: String): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecasts: List<ForecastEntity>)

    @Query("DELETE FROM forecast WHERE city = :city")
    suspend fun deleteForecastByCity(city: String)
}

