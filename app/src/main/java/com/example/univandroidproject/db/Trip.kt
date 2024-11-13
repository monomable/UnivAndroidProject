package com.example.univandroidproject.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_table")
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val contents: String,
    val start_day: Int,
    val end_day: Int,
    val thumbnail: Int,
    val images: Int
){
    constructor(): this(0, "main", "contents", 241113, 241114, 0,1)
}
