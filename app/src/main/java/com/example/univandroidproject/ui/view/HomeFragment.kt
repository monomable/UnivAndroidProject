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

class HomeFragment: Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //return inflater.inflate(R.layout.fragment_home, container, false)
        //fragment_home과 연결시켜 return해줌.

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val btn: Button = view.findViewById(R.id.addButton)

        btn.setOnClickListener(this)
        return view
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addButton -> {
                //Toast.makeText(activity, "버튼 눌림", Toast.LENGTH_SHORT).show()

                //fragment 내부 간의 이동
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_container, AddFragment()).commit()
                true // 뒤로가기가 안됨..
                //val intent = Intent(getActivity(), AddActivity::class.java)
                //startActivity(intent)
            }

            else -> {
            }
        }
    }
}