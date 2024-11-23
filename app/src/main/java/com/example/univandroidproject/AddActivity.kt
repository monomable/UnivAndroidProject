package com.example.univandroidproject

import AddTripAdapter
import android.Manifest
import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.icu.text.CaseMap.Title
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        binding.saveButton.setOnClickListener {
            val title = binding.title.text.toString()
            val contents = binding.contents.text.toString()
            //val startDay = Integer.parseInt(binding.startdayButton.text.toString())
            //val endDay = Integer.parseInt(binding.enddayButton.text.toString())
            val startDay = binding.startdayButton.text.toString()
            val endDay = binding.enddayButton.text.toString()

            //비어있는지 확인
            if (title.isNotEmpty() || contents.isNotEmpty() || startDay.isNotEmpty() || endDay.isNotEmpty()) { // != null
                saveTrip(title, contents, startDay, endDay)
            }
        }



        recyclerInit() //리사이클러 뷰 생성 코드

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

    private fun saveTrip(title: String, contents: String, startDay: String, endDay: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.tripDao().insert(Trip(tripTitle = title, tripContents = contents, tripStartDay = startDay, tripEndDay = endDay))
            //Toast.makeText(this@AddActivity, "저장 완료!", Toast.LENGTH_SHORT).show()
            // mainactivity로 이동 해야함
        }

    }

    private fun recyclerInit() {
        recyclerView = findViewById(R.id.img_board)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        ImgList = ArrayList()

        addDataToList()

        imgAdapter = AddTripAdapter(ImgList)
        recyclerView.adapter = imgAdapter


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