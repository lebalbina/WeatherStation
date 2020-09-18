package com.malwina.weatherstation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malwina.weatherstation.database.recentsearch.RecentSearchData
import com.malwina.weatherstation.database.recentsearch.RecentSearchDataDao

@Database(entities = [RecentSearchData::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDataDao

    companion object {
        fun create(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }
}