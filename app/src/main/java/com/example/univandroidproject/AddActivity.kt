package com.example.univandroidproject

import AddImgAdapter
import android.Manifest
import android.app.DatePickerDialog
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.databinding.ActivityAddBinding
import com.example.univandroidproject.ui.Recycler.ImgItem
import com.example.univandroidproject.ui.view.DatePickerFragment

class AddActivity : AppCompatActivity() {

    lateinit var startDaybutton : Button
    lateinit var endDaybutton : Button
    lateinit var file_img : ImageView
    lateinit var file_img2 : ImageView
    lateinit var file_img3 : ImageView

    lateinit var recyclerView: RecyclerView
    lateinit var ImgList:ArrayList<ImgItem>
    lateinit var imgAdapter: AddImgAdapter

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
        setContentView(R.layout.activity_add)

        recyclerInit()

        checkPermission.launch(permissionList)


        //file_img = findViewById<ImageView>(R.id.imageView)

        //file_img.setOnClickListener {
        //    readImage.launch("image/*")
        //}



        startDaybutton = findViewById<Button>(R.id.startday_Button)
        endDaybutton = findViewById<Button>(R.id.endday_Button)

        startDaybutton.setOnClickListener{

            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")

            //startDaybutton.text = newFragment.getText()
            //daypickbutton.text = "$year/${month+1}/$dayOfMonth"
            //Toast.makeText(this, "날짜 선택 버튼 눌림", Toast.LENGTH_SHORT).show()
        }

        endDaybutton.setOnClickListener{
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

    }

    private fun recyclerInit() {
        recyclerView = findViewById(R.id.img_board)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        ImgList = ArrayList()

        addDataToList()

        imgAdapter = AddImgAdapter(ImgList)
        recyclerView.adapter = imgAdapter


    }

    private fun addDataToList() {
        ImgList.add(ImgItem(R.drawable.outline_add_24, "추가"))
        ImgList.add(ImgItem(R.drawable.outline_add_24, "추가"))
        ImgList.add(ImgItem(R.drawable.outline_add_24, "추가"))
        ImgList.add(ImgItem(R.drawable.outline_add_24, "추가"))
        ImgList.add(ImgItem(R.drawable.outline_add_24, "추가"))
        ImgList.add(ImgItem(R.drawable.outline_add_24, "추가"))
    }
}