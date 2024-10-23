package com.example.univandroidproject

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    lateinit var daypickbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)

        daypickbutton = findViewById<Button>(R.id.dayPicker_Button)
        daypickbutton.setOnClickListener{
            Toast.makeText(this, "날짜 선택 버튼 눌림", Toast.LENGTH_SHORT).show()
        }

    }
}