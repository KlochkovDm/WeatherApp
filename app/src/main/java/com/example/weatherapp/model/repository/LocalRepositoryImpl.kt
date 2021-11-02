package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.model.data.convertHistoryEntityToWeather
import com.example.weatherapp.model.data.convertWeatherToEntity
import com.example.weatherapp.room.HistoryDao

class LocalRepositoryImpl(private val localDataSource:  HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        return localDataSource.insert(convertWeatherToEntity(weather))
    }
}