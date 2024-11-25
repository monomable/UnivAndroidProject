package com.example.univandroidproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application){
    val readAllData : LiveData<List<Trip>>
    private val repository : TripRepository

    init {
        val tripDao = TripRoomDatabase.getDatabase(application).tripDao()
        repository = TripRepository(tripDao)
        readAllData = repository.readAllData
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrip(trip)
        }
    }

}