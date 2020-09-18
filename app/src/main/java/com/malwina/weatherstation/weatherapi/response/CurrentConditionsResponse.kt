package com.malwina.weatherstation.weatherapi.response

import com.google.gson.annotations.SerializedName

data class CurrentConditionsResponse(
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("WeatherText")
    val weatherText: String,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int?,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String?,
    @SerializedName("Temperature")
    val temperature: TemperatureCurrentConditions,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature : RealFeelTemperature,
    @SerializedName("RelativeHumidity")
    val relativeHumidity: Int?
)

data class TemperatureCurrentConditions(
    @SerializedName("Metric")
    val metric: Metric,
    @SerializedName("Imperial")
    val imperial: Imperial
)

data class Metric(
    @SerializedName("Value")
    val value: Double?,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int
)

data class Imperial(
    @SerializedName("Value")
    val value: Double?,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int
)

data class RealFeelTemperature(
    @SerializedName("Metric")
    val metric: Metric,
    @SerializedName("Imperial")
    val imperial: Imperial
)
