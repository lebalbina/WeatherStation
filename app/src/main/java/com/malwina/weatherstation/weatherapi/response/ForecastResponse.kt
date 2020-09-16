package com.malwina.weatherstation.weatherapi.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("Headline")
    val headline: Headline,
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecasts>
)

data class Headline(
    @SerializedName("EffectiveDate")
    val effectiveDate: String,
    @SerializedName("EffectiveEpochDate")
    val effectiveEpochDate: Long,
    @SerializedName("Severity")
    val severity: Int,
    @SerializedName("Text")
    val text: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("EndTime")
    val endDate: String?,
    @SerializedName("EndEpochDate")
    val endEpochDate: Long,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    val link: String
)

data class DailyForecasts(
    @SerializedName("Date")
    val date: String,
    @SerializedName("EpochDate")
    val epochDate: Long,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("Day")
    val day: Day,
    @SerializedName("Night")
    val night: Night,
    @SerializedName("Sources")
    val sources: List<String>,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    val link: String
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

data class Day(
    @SerializedName("icon")
    val icon: Int,
    @SerializedName("iconPhrase")
    val iconPhrase: String,
    @SerializedName("hasPrecipitation")
    val hasPrecipitation: Boolean
)

data class Night(
    @SerializedName("icon")
    val icon: Int,
    @SerializedName("iconPhrase")
    val iconPhrase: String,
    @SerializedName("hasPrecipitation")
    val hasPrecipitation: Boolean
)