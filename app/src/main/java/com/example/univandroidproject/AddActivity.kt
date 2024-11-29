package com.example.univandroidproject

import AddTripAdapter
import android.Manifest
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Instances
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.data.ImageEntity
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class AddActivity : AppCompatActivity() {

    lateinit var startDaybutton : Button
    lateinit var endDaybutton : Button
    //lateinit var file_img : ImageView

    lateinit var recyclerView: RecyclerView
    lateinit var ImgList:ArrayList<Trip>
    lateinit var imgAdapter: AddTripAdapter

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

    /*
    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        file_img.setImageURI(uri)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = TripRoomDatabase.getDatabase(this)


        val sampleData = listOf("Image 1", "Image 2", "Image 3")
        adapter = AddTripAdapter(sampleData) { onRecyclerViewItemClicked() }

        binding.imgBoard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.imgBoard.adapter = adapter


        binding.saveButton.setOnClickListener {
            val title = binding.title.text.toString()
            val contents = binding.contents.text.toString()
            //val startDay = Integer.parseInt(binding.startdayButton.text.toString())
            //val endDay = Integer.parseInt(binding.enddayButton.text.toString())
            val startDay = binding.startdayButton.text.toString()
            val endDay = binding.enddayButton.text.toString()
            val img = BitmapFactory.decodeResource(resources, R.drawable.plusicon)

            //비어있는지 확인
            if (title.isNotEmpty() || contents.isNotEmpty() || startDay.isNotEmpty() || endDay.isNotEmpty()) { // != null
                saveTrip(title, contents, startDay, endDay, img)
            }
        }



        //recyclerInit() //리사이클러 뷰 생성 코드

        checkPermission.launch(permissionList)

        //file_img = findViewById<ImageView>(R.id.imageView)

        //file_img.setOnClickListener {
        //    readImage.launch("image/*")
        //}

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
        CoroutineScope(Dispatchers.IO).launch {  // 코루틴 사용 비동기로 실행
            database.tripDao().insert(Trip(tripTitle = title, tripContents = contents, tripStartDay = startDay, tripEndDay = endDay, tripImage = img))
            //Toast.makeText(this@AddActivity, "저장 완료!", Toast.LENGTH_SHORT).show()

        }
        finish() // mainactivity로 이동

    }



    /* (구)리사이클러뷰 샘플 이미지 삽입 코드
    private fun recyclerInit() {
        recyclerView = findViewById(R.id.img_board)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        ImgList = ArrayList()

        addDataToList()

        imgAdapter = AddTripAdapter(ImgList)
        recyclerView.adapter = imgAdapter


    }*/

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { saveImageToDatabase(it) }
    }

    private fun onRecyclerViewItemClicked() {
        // Launch image picker
        selectImageLauncher.launch("image/*")
    }

    private fun saveImageToDatabase(uri: Uri) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.tripDao().insertImage(ImageEntity(imagePath = uri.toString()))
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



    private fun addDataToList() {  // 리사이클러뷰 데이터 추가
        val bm = BitmapFactory.decodeResource(resources, R.drawable.plusicon)

        ImgList.add(Trip(0,"추가", "추가", "20240101", "20240101"))
        ImgList.add(Trip(0,"추가", "추가", "20240101", "20240101"))
        ImgList.add(Trip(0,"추가", "추가", "20240101", "20240101"))
        ImgList.add(Trip(0,"추가", "추가", "20240101", "20240101"))
        ImgList.add(Trip(0,"추가", "추가", "20240101", "20240101"))
        //ImgList.add(Trip(0,"추가", "추가", "20240101", "20240101", bm))
    }
}