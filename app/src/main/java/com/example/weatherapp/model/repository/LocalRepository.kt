package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.Weather

interface LocalRepository {
    fun getAllHistory():List<Weather>
    fun saveEntity(weather: Weather)
}