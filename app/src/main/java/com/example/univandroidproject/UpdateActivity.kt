package com.example.univandroidproject

import ImageAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.univandroidproject.data.ImageEntity
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripDao
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.databinding.ActivityAddBinding
import com.example.univandroidproject.ui.Recycler.MarginItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var tripDao: TripDao
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = mutableListOf<ImageEntity?>()

    lateinit var startDaybutton : Button
    lateinit var endDaybutton : Button

    private val calendar = Calendar.getInstance()

    private var tripId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DAO
        val database = TripRoomDatabase.getDatabase(this)
        tripDao = database.tripDao()

        // Retrieve Trip ID from Intent
        tripId = intent.getLongExtra("TRIP_ID", -1L)

        if (tripId == -1L) {
            Toast.makeText(this, "ID를 찾을 수 없습니다", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize RecyclerView
        imageAdapter = ImageAdapter(imageList, this::onAddImageClick, this::onImageClick)
        binding.imgBoard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.imgBoard.adapter = imageAdapter
        binding.imgBoard.addItemDecoration(MarginItemDecoration(20))

        // Load Trip and Images Data
        loadTripData()
        loadImagesFromDatabase()

        // Set update button click listener
        binding.saveButton.setOnClickListener {
            updateTripData()
        }

        startDaybutton = findViewById<Button>(R.id.startday_Button)
        endDaybutton = findViewById<Button>(R.id.endday_Button)

        startDaybutton.setOnClickListener{
            showDatePicker(startDaybutton)
        }

        endDaybutton.setOnClickListener{
            showDatePicker(endDaybutton)
        }
    }

    private fun loadTripData() {
        lifecycleScope.launch {
            tripDao.getItem(tripId).collect {
                it?.let {
                    binding.title.setText(it.tripTitle)
                    binding.contents.setText(it.tripContents)
                    binding.tag.setText(it.tripTag)
                    binding.startdayButton.setText(it.tripStartDay)
                    binding.enddayButton.setText(it.tripEndDay)
                }
            }
        }
    }

    private fun loadImagesFromDatabase() {
        lifecycleScope.launch {
            val images = tripDao.getImagesByTripId(tripId.toLong())
            imageList.clear()
            imageList.addAll(images)
            imageList.add(null) // Placeholder for adding new images
            imageAdapter.notifyDataSetChanged()
        }
    }

    private fun updateTripData() {
        val updatedTitle = binding.title.text.toString()
        val updatedContents = binding.contents.text.toString()
        val updateTag = binding.contents.text.toString()
        val updatedStartDay = binding.startdayButton.text.toString()
        val updatedEndDay = binding.enddayButton.text.toString()


        if (updatedTitle.isEmpty() || updatedContents.isEmpty() || updateTag.isEmpty() || updatedStartDay.isEmpty() || updatedEndDay.isEmpty()) {
            Toast.makeText(this, "모든 값을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedTrip = Trip(
            id = tripId,
            tripTitle = updatedTitle,
            tripContents = updatedContents,
            tripTag = updateTag,
            tripStartDay = updatedStartDay,
            tripEndDay = updatedEndDay
        )

        lifecycleScope.launch(Dispatchers.IO) {
            tripDao.update(updatedTrip)

            val updatedImages = imageList.filterNotNull().map { it.copy(tripId = tripId.toLong()) }
            tripDao.deleteImagesByTripId(tripId.toLong())
            tripDao.insertImages(updatedImages)

            runOnUiThread {
                setResult(RESULT_OK)
                Toast.makeText(this@UpdateActivity, "여행이 업데이트 되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun onAddImageClick() {
        selectImage()
    }

    private fun onImageClick(position: Int) {
        val imageEntity = imageList[position]
        if (imageEntity != null) {
            lifecycleScope.launch {
                deleteImageFromInternalStorage(imageEntity.imageKey)
                imageList.removeAt(position)
                imageAdapter.notifyItemRemoved(position)
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                val imageKey = saveImageToInternalStorage(bitmap)

                if (imageKey != null) {
                    val newImage = ImageEntity(tripId = 0L, imageKey = imageKey)
                    imageList.add(imageList.size - 1, newImage)
                    imageAdapter.notifyItemInserted(imageList.size - 2)
                }
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap?): String? {
        if (bitmap == null) {
            return null
        }
        val directory = filesDir
        val imageKey = UUID.randomUUID().toString()
        val filePath = "$directory/$imageKey.png"

        try {
            val stream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imageKey
    }

    private fun deleteImageFromInternalStorage(imageKey: String?): Boolean {
        if (imageKey == null) {
            return false
        }

        val directory = filesDir
        val filePath = "$directory/$imageKey.png"
        val file = File(filePath)

        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    private fun showDatePicker(button: Button) {
        val datePickerDialog = DatePickerDialog(this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())
            val foramattedDate = dateFormat.format(selectedDate.time)
            button.text = foramattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}

