package com.example.univandroidproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.univandroidproject.databinding.ActivityMainBinding
import com.example.univandroidproject.databinding.ActivitySaveBinding
import com.example.univandroidproject.db.TripAddDatabase
import com.example.univandroidproject.db.Trip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySaveBinding
    private lateinit var database: TripAddDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = TripAddDatabase.getDatabase(this)

        binding.saveButton.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNotEmpty()) {
                saveText(text)
            }
        }

        binding.getButon.setOnClickListener {
            loadTexts()
        }

        binding.deleteButton.setOnClickListener {
            deleteTexts()
        }
    }

    private fun saveText(text:String){
        CoroutineScope(Dispatchers.IO).launch {
            database.travelDao().insert(Trip(title = text, contents = "contents", end_day = 0, start_day = 0))
            binding.edit.text = null
        }
    }

    private fun loadTexts() {
        CoroutineScope(Dispatchers.IO).launch {
            val texts = database.travelDao().getAll().joinToString("\n") { it.title }
            withContext(Dispatchers.Main) {
                binding.view.text = texts
            }
        }
    }

    private fun deleteTexts(){
        CoroutineScope(Dispatchers.IO).launch {
            database.travelDao().deleteAll()
            loadTexts()
        }
    }
}