package com.example.univandroidproject.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.univandroidproject.AddActivity
import com.example.univandroidproject.MainActivity
import com.example.univandroidproject.R

class MapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
        //fragment_map과 연결시켜 return해줌.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mvbutton: Button = view.findViewById<Button>(R.id.NewButton)
        mvbutton.setOnClickListener{
            val intent = Intent (getActivity(), AddActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}