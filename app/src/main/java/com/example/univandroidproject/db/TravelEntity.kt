package com.example.univandroidproject.db
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TravelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String
)
