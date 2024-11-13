package com.example.univandroidproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg trip: Trip)

    @Query("SELECT * FROM trip_table")
    fun getAll(): List<Trip>

    @Query("DELETE FROM trip_table")
    suspend fun deleteAll()
}