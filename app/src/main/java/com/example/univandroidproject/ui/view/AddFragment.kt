package com.example.univandroidproject.ui.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.univandroidproject.AddActivity
import com.example.univandroidproject.R
import com.example.univandroidproject.MainActivity
import java.util.Calendar

class AddFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
        //fragment_add과 연결시켜 return해줌.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daypickbutton: Button = view.findViewById<Button>(R.id.dayPicker_Button)
        daypickbutton.setOnClickListener{
            Toast.makeText(AddActivity(), "날짜 선택 버튼 눌림", Toast.LENGTH_SHORT).show()
        }
    }

}