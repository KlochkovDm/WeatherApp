package com.example.weatherapp.model.data

import com.example.weatherapp.model.data.dto.WeatherDTO

fun convertDtoToModel (weatherDTO : WeatherDTO) : List<Weather> {
    val fact = weatherDTO.fact!!
    return listOf(
        Weather(
            getDefaultCity(),
            fact.temp!!,
            fact.feels_like!!,
            fact.condition!!
        )
    )
}