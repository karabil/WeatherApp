package com.example.weather_app_clone.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app_clone.data.model.Main
import com.example.weather_app_clone.data.model.WeatherResponse
import com.example.weather_app_clone.data.repository.MyRepositoryImpl
import com.example.weather_app_clone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: dagger.Lazy<MyRepositoryImpl>,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(
) {
    val responseFlow =
        savedStateHandle.getStateFlow("response", WeatherResponse("", null))
    val textFlow = savedStateHandle.getStateFlow("text", "")
    val progressFlow = savedStateHandle.getStateFlow("progress", false)
    val errorFlow = savedStateHandle.getStateFlow("error", "")

    fun updateTextFlow(textToUpdate: String) {
        savedStateHandle["text"] = textToUpdate
        savedStateHandle["error"] = ""
        savedStateHandle["response"] = WeatherResponse("", null)
    }

    fun getTemperatureByCityName(city: String) {
        viewModelScope.launch {
            savedStateHandle["progress"] = true
            delay(2000)
            when (val result =
                repository.get().getWeatherByCity(city, "5e3eeecf7156ae2fbee20b369e2584a0")) {
                is Resource.Success -> {
                    //_responseStateFlow.value = result.data as WeatherResponse
                    val responseData = result.data as WeatherResponse
                    val weatherResponse =
                        WeatherResponse(city, responseData.main?.temp?.let { Main(it) })
                    savedStateHandle["response"] = weatherResponse
                    savedStateHandle["progress"] = false
                }
                is Resource.Error -> {
                    savedStateHandle["progress"] = false
                    savedStateHandle["error"] = result.message
                }
                else -> {}
            }
        }
    }
}