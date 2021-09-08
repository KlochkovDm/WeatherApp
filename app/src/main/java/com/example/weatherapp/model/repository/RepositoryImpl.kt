package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.model.data.getRussianCities
import com.example.weatherapp.model.data.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()

    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

}