package com.malwina.weatherstation.weatherapi.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecasts>
)

data class DailyForecasts(
    @SerializedName("Temperature")
    val temperature: Temperature
)

data class Temperature(
    @SerializedName("Minimum")
    val minimum: Minimum,
    @SerializedName("Maximum")
    val maximum: Maximum
)

data class Minimum(
    @SerializedName("Value")
    val value: Double?,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int
)

data class Maximum(
    @SerializedName("Value")
    val value: Double?,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int
)