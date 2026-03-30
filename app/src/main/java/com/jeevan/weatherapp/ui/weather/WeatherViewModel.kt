package com.jeevan.weatherapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeevan.weatherapp.data.network.NetworkObserver
import com.jeevan.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel – processes [WeatherIntent]s and emits [WeatherState].
 */
class WeatherViewModel(
    private val repository: WeatherRepository,
    networkObserver: NetworkObserver,
    private val mapper: WeatherViewMapper
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            networkObserver.isOnline.collect { online ->
                val previous = _state.value
                _state.update { it.copy(isOnline = online) }

                // Auto-refresh when internet comes back while showing cached data
                if (online && previous.isOfflineData && previous.city.isNotBlank()) {
                    fetchForecast(previous.city)
                }
            }
        }
    }

    fun processIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.SearchCity -> fetchForecast(intent.city)
            is WeatherIntent.Refresh -> fetchForecast(_state.value.city)
            is WeatherIntent.DismissError -> _state.update { it.copy(error = null) }
        }
    }

    private fun fetchForecast(city: String) {
        if (city.isBlank()) {
            _state.update { it.copy(error = mapper.emptyCity()) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, city = city) }

            repository.getForecast(city).fold(
                onSuccess = { result ->
                    _state.update {
                        it.copy(
                            forecasts = result.forecasts,
                            isLoading = false,
                            error = null,
                            isOfflineData = result.isOffline
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            forecasts = emptyList(),
                            isLoading = false,
                            error = mapper.errorMessage(error),
                            isOfflineData = false
                        )
                    }
                }
            )
        }
    }
}
