package com.example.univandroidproject.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.univandroidproject.R
import com.example.univandroidproject.databinding.FragmentAddBinding

class AddFragment : Fragment(), View.OnClickListener {

    private  lateinit var binding: FragmentAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add, container, false)
        //fragment_add과 연결시켜 return해줌.
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}