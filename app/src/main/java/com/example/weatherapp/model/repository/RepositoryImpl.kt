package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.Weather

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()

    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }
}