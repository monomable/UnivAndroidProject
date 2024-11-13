package com.example.univandroidproject.data

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
    @ColumnInfo(name = "thumbnail")
    val tripThumbnail: Int,
    @ColumnInfo(name = "image")
    val tripImages: Int
)

fun Trip.getFormattedId(): String =
    NumberFormat.getCurrencyInstance().format(id)
