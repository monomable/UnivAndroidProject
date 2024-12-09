package com.example.univandroidproject.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val tripTitle: String = "",
    @ColumnInfo(name = "contents")
    val tripContents: String = "",
    @ColumnInfo(name = "tag")
    val tripTag: String = "",
    @ColumnInfo(name = "start_day")
    val tripStartDay: String = "",
    @ColumnInfo(name = "end_day")
    val tripEndDay: String = ""
)