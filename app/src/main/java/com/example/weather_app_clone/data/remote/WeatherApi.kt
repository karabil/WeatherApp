package com.example.weather_app_clone.data.remote

import com.example.weather_app_clone.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Response<WeatherResponse>
}