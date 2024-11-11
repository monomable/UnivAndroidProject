package com.example.univandroidproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TravelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg travelEntity: TravelEntity)

    @Query("SELECT * FROM TravelEntity")
    suspend fun getAll(): List<TravelEntity>

    @Query("DELETE FROM TravelEntity")
    suspend fun deleteAll()
}