package com.example.univandroidproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Trip::class], version = 1, exportSchema = false)
abstract class TripAddDatabase :RoomDatabase(){
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: TripAddDatabase? = null

        fun getDatabase(context: Context): TripAddDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripAddDatabase::class.java,
                    "travel-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}