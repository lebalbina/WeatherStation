package com.malwina.weatherstation.model

data class WeatherDetails(
    val minTemp: String,
    val maxTemp: String,
    val currentTemp: String,
    val link: String?,
    val tempColor: Int,
    val weatherText: String?,
    val realFeel: String?,
    val humidity: Int?,
    val hasPrecipitation: Boolean,
    val precipitationType: String?
)