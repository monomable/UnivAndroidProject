package com.example.univandroidproject.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.univandroidproject.R
import com.example.univandroidproject.databinding.ActivityMainBinding

class FavoriteFragment: Fragment() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favorite, container, false)
        //fragment_home과 연결시켜 return해줌.

    }

}