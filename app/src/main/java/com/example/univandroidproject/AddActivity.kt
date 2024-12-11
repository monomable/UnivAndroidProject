package com.example.univandroidproject

import ImageAdapter
import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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


class AddActivity : AppCompatActivity() {

    lateinit var startDaybutton : Button
    lateinit var endDaybutton : Button

    private val calendar = Calendar.getInstance()

    private lateinit var binding: ActivityAddBinding
    private lateinit var adapter: ImageAdapter
    private lateinit var database: TripRoomDatabase
    private lateinit var tripDao: TripDao

    //private val tripId: Long = 1L
    //private val selectedImages = mutableListOf<Uri>()

    private val imageList = mutableListOf<ImageEntity?>() // RecyclerView와 데이터 동기화를 위한 리스트

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) // 이미지 접근 권한 확인
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(this, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                //finish()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TripRoom Database 초기화
        database = TripRoomDatabase.getDatabase(this)
        tripDao = database.tripDao()

        adapter = ImageAdapter(imageList, this::onAddImageClick, this::onImageClick)

        binding.imgBoard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.imgBoard.adapter = adapter
        binding.imgBoard.addItemDecoration(MarginItemDecoration(20))

        loadImagesFromDatabase()

        binding.saveButton.setOnClickListener {
            val title = binding.title.text.toString()
            val contents = binding.contents.text.toString()
            val tag = binding.tag.text.toString()
            val startDay = binding.startdayButton.text.toString()
            val endDay = binding.enddayButton.text.toString()
            //val img = BitmapFactory.decodeResource(resources, R.drawable.plusicon)

            //비어있는지 확인
            if (title.isNotEmpty() || contents.isNotEmpty() || startDay.isNotEmpty() || endDay.isNotEmpty()) { // != null
                saveTripWithImages(title, contents, tag, startDay, endDay)
            }

            // imageEntitiy에 trip id로 이미지 저장
        }

        checkPermission.launch(permissionList)

        startDaybutton = findViewById<Button>(R.id.startday_Button)
        endDaybutton = findViewById<Button>(R.id.endday_Button)

        startDaybutton.setOnClickListener{
            showDatePicker(startDaybutton)
        }

        endDaybutton.setOnClickListener{
            showDatePicker(endDaybutton)
        }

    }

    private fun saveTripWithImages(
        title: String,
        contents: String,
        tag: String,
        startDay: String,
        endDay: String,
    ){
        lifecycleScope.launch(Dispatchers.IO) {
            // Trip을 먼저 저장
            val tripId = database.tripDao().insert(Trip(
                tripTitle = title,
                tripContents = contents,
                tripTag = tag,
                tripStartDay = startDay,
                tripEndDay = endDay
            ))

            // 저장된 Trip ID와 연결하여 이미지를 저장
            val imagesToSave = imageList.filterNotNull().map { it.copy(tripId = tripId) }
            tripDao.insertImages(imagesToSave)

            Toast.makeText(this@AddActivity, "Trip and images saved successfully!", Toast.LENGTH_SHORT).show()

            runOnUiThread { finish() } // MainActivity로 이동
        }

    }

    private fun loadImagesFromDatabase() {
        lifecycleScope.launch {
            //val images = tripDao.getImagesByTripId(tripId)
            imageList.clear()
            //imageList.addAll(images)
            imageList.add(null) // "추가 아이콘" 추가
            adapter.notifyDataSetChanged()
        }
    }

    private fun onAddImageClick() {
        selectImage()
    }

    // 이미지 클릭시 삭제
    private fun onImageClick(position: Int) {
        val imageEntity = imageList[position]
        if (imageEntity != null) {
            lifecycleScope.launch {
                //tripDao.deleteImage(tripId, imageEntity.imageKey)
                deleteImageFromInternalStorage(this@AddActivity, imageEntity.imageKey)
                imageList.removeAt(position)
                adapter.notifyItemRemoved(position)
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


    //이미지 로컬에 저장
    private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap?): String? {
        if(bitmap == null) {
            return null
        }
        val directory = context.filesDir
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

    //이미지 로컬에서 삭제
    private fun deleteImageFromInternalStorage(context: Context, imageKey: String?): Boolean {
        if (imageKey == null) {
            return false
        }

        val directory = context.filesDir
        val filePath = "$directory/$imageKey.png"
        val file = File(filePath)

        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                val imageKey = saveImageToInternalStorage(this, bitmap)

                if (imageKey != null) {
                    val newImage = ImageEntity(tripId = 0L, imageKey = imageKey) // 임시 tripId
                    imageList.add(imageList.size - 1, newImage)
                    adapter.notifyItemInserted(imageList.size - 2)
                }
            }
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