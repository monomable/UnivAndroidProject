package com.example.univandroidproject

import AddTripAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.univandroidproject.databinding.ActivitySaveBinding
import com.example.univandroidproject.db.Trip
import javax.sql.DataSource


class SaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_save)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        fillRecyclerview(1)

        // 뷰 바인딩으로 연결해야함, 원본은 kotlin-android-extensions 으로 참조했음
        binding.btnGrid.setOnClickListener{
            fillRecyclerview(2)
        }
        binding.btnLinear.setOnClickListener{
            fillRecyclerview(1)
        }


    }
    private fun fillRecyclerview(recyclerviewType:Int){

        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        if(recyclerviewType==2){
            binding.recyclerview.layoutManager = GridLayoutManager(this,2)
        }
        binding.recyclerview.adapter = AddTripAdapter(this, DataSource.Trip,recyclerviewType){
                trip ->  Toast.makeText(this,"Title : ${trip.title}", Toast.LENGTH_SHORT).show()
        }

    }
}