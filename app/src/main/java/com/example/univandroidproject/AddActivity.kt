package com.example.univandroidproject

import android.Manifest
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.univandroidproject.ui.view.DatePickerFragment

class AddActivity : AppCompatActivity() {

    lateinit var daypickbutton : Button
    lateinit var file_img : ImageView
    lateinit var file_img2 : ImageView
    lateinit var file_img3 : ImageView

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(this, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                //finish()
            }

        }
    }

    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        file_img.setImageURI(uri)
    }

    private val readImage2 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        file_img2.setImageURI(uri)
    }

    private val readImage3 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        file_img3.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)

        checkPermission.launch(permissionList)

        file_img = findViewById<ImageView>(R.id.imageView)
        file_img2 = findViewById<ImageView>(R.id.imageView2)
        file_img3 = findViewById<ImageView>(R.id.imageView3)

        file_img.setOnClickListener {
            readImage.launch("image/*")
        }

        file_img2.setOnClickListener {
            readImage2.launch("image/*")
        }

        file_img3.setOnClickListener {
            readImage3.launch("image/*")
        }



        daypickbutton = findViewById<Button>(R.id.startday_Button)
        daypickbutton.setOnClickListener{

            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")

            //daypickbutton.text = "$year/${month+1}/$dayOfMonth"

            //Toast.makeText(this, "날짜 선택 버튼 눌림", Toast.LENGTH_SHORT).show()
        }

    }
}