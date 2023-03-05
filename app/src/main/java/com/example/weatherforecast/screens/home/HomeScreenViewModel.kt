package com.example.weatherforecast.screens.home

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    suspend fun getWeatherData(cityName: String): DataOrException<WeatherResponse, Boolean, Exception> {
        return weatherRepository.getWeatherDetails(cityName)
    }
}