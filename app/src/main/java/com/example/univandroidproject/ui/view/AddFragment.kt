package com.example.univandroidproject.ui.view

import android.Manifest
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.univandroidproject.AddActivity
import com.example.univandroidproject.R
import com.example.univandroidproject.MainActivity
import com.google.android.gms.maps.MapView
import java.util.Calendar

class AddFragment : Fragment(){

    private lateinit var file_img : ImageView

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(activity, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                //finish()
            }

        }
    }

    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        file_img.setImageURI(uri)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
        //fragment_add과 연결시켜 return해줌.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission.launch(permissionList)

        val file_img = view.findViewById<ImageView>(R.id.imageView)

        file_img.setOnClickListener {
            //Toast.makeText(AddActivity(), "날짜 선택 버튼 눌림", Toast.LENGTH_SHORT).show()
            readImage.launch("image/*")

        }

        val daypickbutton: Button = view.findViewById<Button>(R.id.dayPicker_Button)
        daypickbutton.setOnClickListener{
            Toast.makeText(AddActivity(), "날짜 선택 버튼 눌림", Toast.LENGTH_SHORT).show()
        }
    }

}