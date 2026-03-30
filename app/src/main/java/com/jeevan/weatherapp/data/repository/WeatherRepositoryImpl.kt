package com.jeevan.weatherapp.data.repository

import com.jeevan.weatherapp.data.local.ForecastDao
import com.jeevan.weatherapp.data.model.ForecastEntity
import com.jeevan.weatherapp.data.remote.GeocodingApiService
import com.jeevan.weatherapp.data.remote.WeatherApiService
import com.jeevan.weatherapp.domain.model.CityNotFoundException
import com.jeevan.weatherapp.domain.model.DayForecast
import com.jeevan.weatherapp.domain.model.ForecastResult
import com.jeevan.weatherapp.domain.model.NoForecastDataException
import com.jeevan.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApiService,
    private val geocodingApi: GeocodingApiService,
    private val forecastDao: ForecastDao
) : WeatherRepository {

    override suspend fun getForecast(city: String): Result<ForecastResult> {
        return try {
            val forecasts = fetchFromRemote(city)
            cacheForecasts(city, forecasts)
            Result.success(ForecastResult(forecasts = forecasts, isOffline = false))
        } catch (e: CityNotFoundException) {
            Result.failure(e)
        } catch (e: Exception) {
            // Network failure → fall back to cached data
            val cached = getCachedForecast(city)
            if (cached.isNotEmpty()) {
                Result.success(ForecastResult(forecasts = cached, isOffline = true))
            } else {
                Result.failure(e)
            }
        }
    }

    private suspend fun fetchFromRemote(city: String): List<DayForecast> {
        val geoResponse = geocodingApi.searchCity(name = city)
        val location = geoResponse.results?.firstOrNull()
            ?: throw CityNotFoundException(city)

        val weatherResponse = weatherApi.getForecast(
            latitude = location.latitude,
            longitude = location.longitude
        )

        val daily = weatherResponse.daily
            ?: throw NoForecastDataException()

        return daily.time.indices.map { i ->
            DayForecast(
                date = daily.time[i],
                temperatureMax = daily.temperatureMax[i],
                temperatureMin = daily.temperatureMin[i],
                weatherCode = daily.weatherCode[i]
            )
        }
    }

    private suspend fun cacheForecasts(city: String, forecasts: List<DayForecast>) {
        val cityLower = city.lowercase().trim()
        forecastDao.deleteForecastByCity(cityLower)
        forecastDao.insertAll(forecasts.map { it.toEntity(cityLower) })
    }

    private suspend fun getCachedForecast(city: String): List<DayForecast> {
        return forecastDao.getForecastByCity(city.lowercase().trim()).map { it.toDomain() }
    }

    private fun DayForecast.toEntity(city: String) = ForecastEntity(
        city = city,
        date = date,
        temperatureMax = temperatureMax,
        temperatureMin = temperatureMin,
        weatherCode = weatherCode
    )

    private fun ForecastEntity.toDomain() = DayForecast(
        date = date,
        temperatureMax = temperatureMax,
        temperatureMin = temperatureMin,
        weatherCode = weatherCode
    )
}
