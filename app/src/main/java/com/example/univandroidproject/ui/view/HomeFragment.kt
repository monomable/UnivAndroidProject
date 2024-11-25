package com.example.univandroidproject.ui.view

import HomeTripAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.AddActivity
import com.example.univandroidproject.MainActivity
import com.example.univandroidproject.R
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.data.TripViewModel
import com.example.univandroidproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment: Fragment(), View.OnClickListener {

    private lateinit var database: TripRoomDatabase
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //return inflater.inflate(R.layout.fragment_home, container, false)
        //fragment_home과 연결시켜 return해줌.

        binding = FragmentHomeBinding.inflate(inflater)

        //val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val btn: Button = binding.addButton

        btn.setOnClickListener(this)


        var database : TripRoomDatabase? = null
        var tripList = mutableListOf<Trip>()

        database = TripRoomDatabase.getDatabase(requireContext())

        CoroutineScope(Dispatchers.IO).launch {  // 코루틴 사용 비동기로 실행
            val savedTrips = database!!.tripDao().getAll()
            if (savedTrips.isNotEmpty()){
                tripList.addAll(savedTrips)
            }
        }

        val adapter = HomeTripAdapter()
        val recyclerView = binding.tripRecyclerView
        //adapter.notifyDataSetChanged()

        recyclerView.adapter = adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        mTripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        mTripViewModel.readAllData.observe(viewLifecycleOwner, Observer { trip ->
            adapter.setData(trip)
        })

        setHasOptionsMenu(true)

        //return view
        return binding.root
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
                //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_container, AddFragment()).commit()
                //true // 뒤로가기가 안됨..
                //val intent = Intent(getActivity(), AddActivity::class.java)
                //startActivity(intent)
                val intent = Intent(getActivity(), AddActivity::class.java)
                getActivity()?.startActivity(intent)
            }

            else -> {
            }
        }
    }

    private fun loadTrip() {
        CoroutineScope(Dispatchers.IO).launch {
            val texts = database.tripDao().getAll().joinToString("\n") { it.tripTitle }
            withContext(Dispatchers.Main) {
                //binding.view.text = texts  // 리사이클러뷰 적용 해야함

            }
        }
    }
}