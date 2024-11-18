package com.example.univandroidproject.db

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val tripTitle: String,
    @ColumnInfo(name = "contents")
    val tripContents: String,
    @ColumnInfo(name = "start_day")
    val tripStartDay: Int,
    @ColumnInfo(name = "end_day")
    val tripEndDay: Int,
    @ColumnInfo(name = "image")
    val tripImage: Bitmap? = null
)