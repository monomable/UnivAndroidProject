package com.example.univandroidproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg travelEntity: Trip)

    @Query("SELECT * FROM Trip")
    suspend fun getAll(): List<Trip>

    @Query("DELETE FROM Trip")
    suspend fun deleteAll()
}