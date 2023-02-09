package com.example.weather_app_clone.domain.repository

import com.example.weather_app_clone.data.models.WeatherResponse
import com.example.weather_app_clone.util.Resource

interface MyRepository {
    suspend fun getWeatherByCity(city: String, appId: String): Resource<WeatherResponse>
}