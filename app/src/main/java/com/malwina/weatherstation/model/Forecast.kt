package com.malwina.weatherstation.model

import com.malwina.weatherstation.weatherapi.response.ForecastResponse

data class Forecast(
    val minTemp: Double?,
    val maxTemp: Double?
)

fun ForecastResponse.toDomain(): Forecast {
    return Forecast(
        minTemp = this.dailyForecasts[0].temperature.minimum.value,
        maxTemp = this.dailyForecasts[0].temperature.minimum.value
    )
}