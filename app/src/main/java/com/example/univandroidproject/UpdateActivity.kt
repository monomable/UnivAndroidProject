package com.example.univandroidproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.data.ImageEntity
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.ui.Recycler.UpdateImageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateActivity : AppCompatActivity() {

    private lateinit var database: TripRoomDatabase
    private lateinit var imageAdapter: UpdateImageAdapter
    private val imageList = mutableListOf<ImageEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tripId = intent.getLongExtra("tripId", -1L)
        val tripTitle = intent.getStringExtra("tripTitle")
        val tripContents = intent.getStringExtra("tripContents")
        val tripStartDay = intent.getStringExtra("tripStartDay")
        val tripTag = intent.getStringExtra("tripTag")
        val tripEndDay = intent.getStringExtra("tripEndDay")

        findViewById<TextView>(R.id.updateTitle).text = tripTitle ?: ""
        findViewById<TextView>(R.id.updateContents).text = tripContents ?: ""
        findViewById<TextView>(R.id.updateTag).text = tripTag ?: ""
        findViewById<TextView>(R.id.update_startday_Button).text = tripStartDay ?: ""
        findViewById<TextView>(R.id.update_endday_Button).text = tripEndDay ?: ""

        // DB 초기화
        database = TripRoomDatabase.getDatabase(this)

        // RecyclerView 셋업
        val recyclerView = findViewById<RecyclerView>(R.id.update_img_board)
        imageAdapter = UpdateImageAdapter(imageList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = imageAdapter

        if (tripId != -1L) {
            loadImages(tripId)
        }
    }

    private fun loadImages(tripId: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            val tripDao = database.tripDao()
            val images = tripDao.getImagesByTripId(tripId)
            withContext(Dispatchers.Main) {
                imageList.clear()
                imageList.addAll(images)
                imageAdapter.notifyDataSetChanged()
            }
        }
    }
}