package com.example.univandroidproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.data.ImageEntity
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.ui.Recycler.MarginItemDecoration
import com.example.univandroidproject.ui.Recycler.DetailImageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var database: TripRoomDatabase
    private lateinit var imageAdapter: DetailImageAdapter
    private val imageList = mutableListOf<ImageEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //val tripId = intent.getLongExtra("tripId", -1L)
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

        // RecyclerView 설정
        val recyclerView = findViewById<RecyclerView>(R.id.update_img_board)
        imageAdapter = DetailImageAdapter(imageList, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = imageAdapter
        recyclerView.addItemDecoration(MarginItemDecoration(20))

        // 전달받은 tripId 확인
        val tripId = intent.getLongExtra("tripId", -1L)
        if (tripId != -1L) {
            loadImagesForTrip(tripId)
        }

        // 삭제 버튼 설정
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            if (tripId != -1L) {
                deleteTrip(tripId)
            } else {
                Toast.makeText(this, "삭제할 수 없습니다. 잘못된 데이터입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 수정 버튼 설정
        val updateButton = findViewById<Button>(R.id.updateSaveButton)
        updateButton.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java).apply {
                putExtra("TRIP_ID", tripId) // Pass the tripId to UpdateActivity
            }
            startActivity(intent)
        }
    }

    private fun loadImagesForTrip(tripId: Long) {
        lifecycleScope.launchWhenStarted {
            val db = TripRoomDatabase.getDatabase(this@DetailActivity)
            val images = withContext(Dispatchers.IO) {
                db.tripDao().getImagesByTripId(tripId) // tripId와 일치하는 이미지 가져오기
            }
            updateImageList(images)
        }
    }

    private fun updateImageList(images: List<ImageEntity>) {
        imageList.clear()
        imageList.addAll(images)
        imageAdapter.notifyDataSetChanged()
    }

    private fun deleteTrip(tripId: Long) {
        lifecycleScope.launch {
            val db = TripRoomDatabase.getDatabase(this@DetailActivity)
            withContext(Dispatchers.IO) {
                db.tripDao().deleteTripById(tripId) // 특정 Trip 삭제
                db.tripDao().deleteImagesByTripId(tripId) // Trip과 관련된 이미지 삭제
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailActivity, "여행 기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK) // HomeFragment로 결과 전달
                finish() // Activity 종료
            }
        }
    }

    /*
    private fun updateTrip(tripId: Long) {
        val title = findViewById<TextView>(R.id.updateTitle).text.toString()
        val contents = findViewById<TextView>(R.id.updateContents).text.toString()
        val tag = findViewById<TextView>(R.id.updateTag).text.toString()
        val startDay = findViewById<TextView>(R.id.update_startday_Button).text.toString()
        val endDay = findViewById<TextView>(R.id.update_endday_Button).text.toString()

        if (title.isEmpty() || startDay.isEmpty() || endDay.isEmpty()) {
            Toast.makeText(this, "필수 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val db = TripRoomDatabase.getDatabase(this@DetailActivity)
            withContext(Dispatchers.IO) {
                val trip = Trip(
                    id = tripId,
                    tripTitle = title,
                    tripContents = contents,
                    tripStartDay = startDay,
                    tripEndDay = endDay,
                    tripTag = tag
                )
                db.tripDao().update(trip)
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailActivity, "여행 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                finish() // Activity 종료
            }
        }
    }*/
}