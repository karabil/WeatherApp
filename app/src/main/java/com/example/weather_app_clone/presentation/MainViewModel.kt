package com.example.weather_app_clone.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app_clone.data.models.Main
import com.example.weather_app_clone.data.models.WeatherResponse
import com.example.weather_app_clone.domain.repository.MyRepository
import com.example.weather_app_clone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: dagger.Lazy<MyRepository>,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(
) {
//    private val _responseState = MutableLiveData<WeatherResponse>()
//    val responseState: LiveData<WeatherResponse> = _responseState

//    private val _responseStateFlow = MutableStateFlow(WeatherResponse(0.toDouble(), ""))
//    val responseStateFlow = _responseStateFlow.asStateFlow()

    val flow = savedStateHandle.getStateFlow("response", (WeatherResponse("", Main(111.toDouble()))))

    fun getTemperatureByCityName(city: String) {
        viewModelScope.launch {
            when (val result =
                repository.get().getWeatherByCity(city, "5e3eeecf7156ae2fbee20b369e2584a0")) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    //_responseStateFlow.value = result.data as WeatherResponse
                    val responseData = result.data as WeatherResponse
                    savedStateHandle["response"] = WeatherResponse(city,Main(convertFtoC(responseData.main.temp)))
                }
                is Resource.Error -> {

                }
                else -> {}
            }
        }
    }

    private fun convertFtoC(degrees: Double) : Double{
//        return (degrees - 32) * 5 / 9
        return degrees
    }
}