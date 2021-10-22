package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.dto.WeatherDTO


interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}