package com.example.univandroidproject.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TravelEntity::class], version = 1)
abstract class TravelAppDatabase :RoomDatabase(){
    abstract fun travelDao(): TravelDao

    companion object {
        @Volatile
        private var INSTANCE: TravelAppDatabase? = null

        fun getDatabase(context: Context): TravelAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelAppDatabase::class.java,
                    "travel-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}