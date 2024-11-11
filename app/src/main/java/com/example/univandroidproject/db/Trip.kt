package com.example.univandroidproject.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val contents: String,
    val start_day: Int,
    val end_day: Int
)
