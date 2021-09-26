package com.example.weatherapp.model.data

import com.example.weatherapp.model.data.dto.WeatherDTO

fun convertDtoToModel (weatherDTO : WeatherDTO) {
    val fact = weatherDTO.fact!!
    listOf(
        Weather(
            getDefaultCity(),
            fact.temp!!,
            fact.feels_like!!,
            fact.condition!!
        )
    )
}