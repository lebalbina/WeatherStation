package com.malwina.weatherstation.model

import com.malwina.weatherstation.weatherapi.response.CurrentConditionsResponse

data class CurrentConditions(
    val currentTemp: Double?,
    val weatherIcon: Int?
)

fun List<CurrentConditionsResponse>.toDomain(): CurrentConditions {
    return CurrentConditions(
        currentTemp = this[0].temperature.metric.value,
        weatherIcon = this[0].weatherIcon
    )
}