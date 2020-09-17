package com.malwina.weatherstation.weatherapi.response

import com.google.gson.annotations.SerializedName
import com.malwina.weatherstation.model.City

data class Location(
    @SerializedName("Version")
    val version: Int,
    @SerializedName("Key")
    val key: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Rank")
    val rank: Int,
    @SerializedName("LocalizedName")
    val localizedName: String,
    @SerializedName("Country")
    val country: Country,
    @SerializedName("AdministrativeArea")
    val administrativeArea: AdministrativeArea
)

data class AdministrativeArea(
    @SerializedName("ID")
    val id: String,
    @SerializedName("LocalizedName")
    val localizedName: String
)


data class Country(
    @SerializedName("ID")
    val id: String,
    @SerializedName("LocalizedName")
    val localizedName: String
)

fun List<Location>.toDomain() : List<City> {
    return this.map {
        City(
            name = it.localizedName,
            id = it.key,
            country = it.country.localizedName,
            area = it.administrativeArea.localizedName
        )
    }
}
