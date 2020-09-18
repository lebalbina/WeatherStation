package com.malwina.weatherstation.database.recentsearch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearchData(
    @PrimaryKey val cityName: String
)