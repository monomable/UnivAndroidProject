package com.example.univandroidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager


class SaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)


        fillRecyclerview(1)

        // 뷰 바인딩으로 연결해야함, 원본은 kotlin-android-extensions 으로 참조했음
        btnGrid.setOnClickListener{
            fillRecyclerview(2)
        }
        btnLinear.setOnClickListener{
            fillRecyclerview(1)
        }


    }
    private fun fillRecyclerview(recyclerviewType:Int){

        recyclerview.layoutManager = LinearLayoutManager(this)

        if(recyclerviewType==2){
            recyclerview.layoutManager = GridLayoutManager(this,2)
        }
        recyclerview.adapter = CountryAdapter(this,DataSource.countries,recyclerviewType){
                country ->  Toast.makeText(this,"Population : ${country.population}", Toast.LENGTH_SHORT).show()
        }

    }
}