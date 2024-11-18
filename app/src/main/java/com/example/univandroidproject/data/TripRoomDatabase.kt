package com.example.univandroidproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Trip::class], version = 1, exportSchema = false)
abstract class TripRoomDatabase :RoomDatabase(){

    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: TripRoomDatabase? = null

        fun getDatabase(context: Context): TripRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripRoomDatabase::class.java,
                    "trip-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}