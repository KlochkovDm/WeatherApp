package com.example.weatherapp.model.repository

import com.example.weatherapp.model.data.dto.WeatherDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :DetailsRepository {

    override fun getWeatherDetailsFromServer(
            lat: Double,
            lon: Double,
            callback: retrofit2.Callback<WeatherDTO>
        ){
        remoteDataSource.getWeatherDetails(lat, lon ,callback)
    }
}