package com.malwina.weatherstation.weatherapi.response

import com.google.gson.annotations.SerializedName

data class CurrentConditionsResponse(
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("EpochTime")
    val epochTime: Long,
    @SerializedName("WeatherText")
    val weatherText: String,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int?,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String?,
    @SerializedName("IsDayTime")
    val isDayTime: Boolean,
    @SerializedName("Temperature")
    val temperature: TemperatureCurrentConditions,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature : RealFeelTemperature
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
