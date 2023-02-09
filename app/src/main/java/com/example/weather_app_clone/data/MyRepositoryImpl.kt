package com.example.weather_app_clone.data

import android.util.Log
import com.example.weather_app_clone.data.models.WeatherResponse
import com.example.weather_app_clone.data.remote.WeatherApi
import com.example.weather_app_clone.domain.repository.MyRepository
import com.example.weather_app_clone.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "MyRepositoryImpl"

class MyRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    /*private val appContext: Application*/
) : MyRepository {
    override suspend fun getWeatherByCity(city: String, appId: String): Resource<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            val response = try {
                weatherApi.getWeatherByCity(city, appId, "metric")
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
                return@withContext Resource.Error(e.message.toString())
            } catch (e: HttpException) {
                Log.e(TAG, e.message.toString())
                return@withContext Resource.Error(e.message().toString())
            }
            if (response.isSuccessful && response.body() != null) {
                return@withContext Resource.Success(response.body()!!)
            } else {
                Log.e(TAG, "Response was not successful")
                return@withContext Resource.Error("Response was not successful")
            }
        }
    }
}
