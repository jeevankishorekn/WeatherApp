package com.jeevan.weatherapp.di

import com.jeevan.weatherapp.data.local.WeatherDatabase
import com.jeevan.weatherapp.data.network.NetworkObserver
import com.jeevan.weatherapp.data.remote.GeocodingApiService
import com.jeevan.weatherapp.data.remote.WeatherApiService
import com.jeevan.weatherapp.data.repository.WeatherRepositoryImpl
import com.jeevan.weatherapp.domain.repository.WeatherRepository
import com.jeevan.weatherapp.ui.weather.WeatherViewMapper
import com.jeevan.weatherapp.ui.weather.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"
private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"

val appModule = module {

    // Network

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single<WeatherApiService> {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    single<GeocodingApiService> {
        Retrofit.Builder()
            .baseUrl(GEOCODING_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }

    // Database

    single { WeatherDatabase.getInstance(androidContext()) }

    single { get<WeatherDatabase>().forecastDao() }

    // Repository

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherApi = get(),
            geocodingApi = get(),
            forecastDao = get()
        )
    }

    // Network Observer

    single { NetworkObserver(androidContext()) }

    // Mapper

    single { WeatherViewMapper(androidContext()) }

    // ViewModel

    viewModel { WeatherViewModel(get(), get(), get()) }
}

