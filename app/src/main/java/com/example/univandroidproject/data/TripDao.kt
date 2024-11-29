package com.example.univandroidproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * from trip ORDER BY start_day ASC")
    fun getItems(): Flow<List<Trip>>

    @Query("SELECT * from trip ORDER BY start_day ASC")
    fun readAllData(): LiveData<List<Trip>>

    @Query("SELECT * from trip WHERE id = :id")
    fun getItem(id: Int): Flow<Trip>

    @Query("SELECT * FROM Trip")
    suspend fun getAll(): List<Trip>

    @Query("DELETE FROM Trip")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip)

    @Update
    suspend fun update(trip: Trip)

    @Delete
    suspend fun delete(trip: Trip)

    @Insert
    suspend fun insertImage(image: ImageEntity)

    @Query("SELECT * FROM images")
    suspend fun getAllImages(): List<ImageEntity>


}