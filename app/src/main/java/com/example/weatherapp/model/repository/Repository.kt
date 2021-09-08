package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.Weather

interface Repository {
    fun getWeatherFromServer() : Weather
    fun getWeatherFromLocalStorageRus () : List<Weather>
    fun getWeatherFromLocalStorageWorld () : List<Weather>
}