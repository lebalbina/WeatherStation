package com.malwina.weatherstation.database.recentsearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RecentSearchDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecentSearch(data: RecentSearchData) : Completable

    @Query("SELECT * FROM RecentSearchData")
    fun getRecentSearches() : Observable<List<RecentSearchData>>
}