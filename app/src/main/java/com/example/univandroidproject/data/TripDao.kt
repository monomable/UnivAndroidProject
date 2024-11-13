package com.example.univandroidproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * from trip ORDER BY id ASC")
    fun getTrips(): Flow<List<Trip>>

    @Query("SELECT * from trip WHERE id = :id")
    fun getTrip(id: Int): Flow<Trip>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg trip: Trip)

    @Query("SELECT * FROM trip")
    fun getAll(): List<Trip>

    @Query("DELETE FROM trip")
    suspend fun deleteAll()
}