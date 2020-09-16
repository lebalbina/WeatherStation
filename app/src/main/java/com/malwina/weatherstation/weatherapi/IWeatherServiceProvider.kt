package com.malwina.weatherstation.weatherapi

interface IWeatherServiceProvider {
    fun getService() : WeatherService
}