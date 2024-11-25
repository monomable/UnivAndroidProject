package com.example.univandroidproject.data

import androidx.lifecycle.LiveData

class TripRepository (private val tripDao: TripDao){
    val readAllData: LiveData<List<Trip>> = tripDao.readAllData()

    suspend fun addTrip(trip: Trip){
        tripDao.addTrip(trip)
    }
}