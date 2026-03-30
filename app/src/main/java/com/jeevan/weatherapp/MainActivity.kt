package com.jeevan.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jeevan.weatherapp.ui.theme.WeatherAppTheme
import com.jeevan.weatherapp.ui.weather.WeatherScreen
import com.jeevan.weatherapp.ui.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}
