package com.malwina.weatherstation.weatherapi.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String,
    val id: String,
    val country: String,
    val area: String
) : Parcelable