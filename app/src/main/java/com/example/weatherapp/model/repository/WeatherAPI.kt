package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.dto.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private const val REQUEST_API_KEY = "X-Yandex-API-Key"

interface WeatherAPI {
    @GET("v2/informers")
        fun getWeather(
            @Header(REQUEST_API_KEY) token: String,
            @Query("lat") lat: Double,
            @Query("lon") lon: Double
        ): Call<WeatherDTO>
}