package com.example.univandroidproject.ui.calendar

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) // ThreeTenABP 초기화
    }
}