package com.example.univandroidproject

import AddTripAdapter
import android.Manifest
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.univandroidproject.data.ImageEntity
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.databinding.ActivityAddBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class AddActivity : AppCompatActivity() {

    lateinit var startDaybutton : Button
    lateinit var endDaybutton : Button

    private val calendar = Calendar.getInstance()

    private lateinit var binding: ActivityAddBinding
    private lateinit var adapter: AddTripAdapter
    private lateinit var database: TripRoomDatabase

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        database = TripRoomDatabase.getDatabase(this)


        val sampleData = listOf("Image 1", "Image 2", "Image 3", "Image 4")
        adapter = AddTripAdapter(sampleData) { position -> onRecyclerViewItemClicked(position) }

        binding.imgBoard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.imgBoard.adapter = adapter


        binding.saveButton.setOnClickListener {
            val title = binding.title.text.toString()
            val contents = binding.contents.text.toString()
            val startDay = binding.startdayButton.text.toString()
            val endDay = binding.enddayButton.text.toString()
            val img = BitmapFactory.decodeResource(resources, R.drawable.plusicon)

            //비어있는지 확인
            if (title.isNotEmpty() || contents.isNotEmpty() || startDay.isNotEmpty() || endDay.isNotEmpty()) { // != null
                saveTrip(title, contents, startDay, endDay, img)
            }
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

    private fun saveTrip(
        title: String,
        contents: String,
        startDay: String,
        endDay: String,
        img: Bitmap
    ){
        lifecycleScope.launch(Dispatchers.IO) {
            // Trip을 먼저 저장
            val tripId = database.tripDao().insert(Trip(
                tripTitle = title,
                tripContents = contents,
                tripStartDay = startDay,
                tripEndDay = endDay,
                tripImage = img
            ))

            // 저장된 Trip ID와 연결하여 이미지를 저장
            selectedImages.forEach { uri ->
                database.tripDao().insertImage(ImageEntity(
                    tripId = tripId, // Trip ID와 연결
                    imagePath = uri.toString()
                ))
            }

            runOnUiThread { finish() } // MainActivity로 이동
        }

    }


    private var currentImagePosition = -1
    private val selectedImages = mutableListOf<Uri>()

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImages.add(it) // 선택된 이미지를 리스트에 추가
            val selectedImageBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it))
            adapter.updateImage(currentImagePosition, selectedImageBitmap)
        }
    }

    private fun onRecyclerViewItemClicked(position: Int) {
        // Launch image picker
        currentImagePosition = position
        selectImageLauncher.launch("image/*")
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