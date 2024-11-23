package com.example.univandroidproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Trip::class], version = 3, exportSchema = false)
@TypeConverters(RoomTypeConverter::class)
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
                    "trip_database"
                )
                    //.fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}