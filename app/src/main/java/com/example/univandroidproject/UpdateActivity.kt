package com.example.univandroidproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // Intent로 전달된 데이터 추출
        val tripTitle = intent.getStringExtra("tripTitle")
        val tripContents = intent.getStringExtra("tripContents")
        val tripStartDay = intent.getStringExtra("tripStartDay")
        val tripEndDay = intent.getStringExtra("tripEndDay")

        // 레이아웃의 TextView에 데이터 표시
        findViewById<TextView>(R.id.updateTitle).text = tripTitle
        findViewById<TextView>(R.id.updateContents).text = tripContents
        findViewById<TextView>(R.id.update_startday_Button).text = tripStartDay
        findViewById<TextView>(R.id.update_endday_Button).text = tripEndDay
    }
}