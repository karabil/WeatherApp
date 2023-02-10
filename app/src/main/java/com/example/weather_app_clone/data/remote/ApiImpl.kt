package com.example.weather_app_clone.data.remote

import com.example.weather_app_clone.data.model.WeatherResponse
import retrofit2.Response

class ApiImpl :WeatherApi{
    override suspend fun getWeatherByCity(
        city: String,
        appId: String,
        units: String
    ): Response<WeatherResponse> {
        TODO("Not yet implemented")
    }
}