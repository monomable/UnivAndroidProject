package com.example.univandroidproject.ui.view

import HomeTripAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.AddActivity
import com.example.univandroidproject.R
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripDao
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.data.TripViewModel
import com.example.univandroidproject.data.TripWithImages
import com.example.univandroidproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment: Fragment(), View.OnClickListener {

    private lateinit var tripDao: TripDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var tripAdapter: HomeTripAdapter

    private val tripList = mutableListOf<TripWithImages>() // Trip 데이터와 관련 이미지를 포함한 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btn: Button = view.findViewById(R.id.addButton)
        btn.setOnClickListener(this)

        val searchButton = view.findViewById<Button>(R.id.home_search_button)
        val searchText = view.findViewById<EditText>(R.id.search_text)

        // RecyclerView 초기화
        recyclerView = view.findViewById(R.id.tripRecyclerView)
        tripAdapter = HomeTripAdapter(tripList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = tripAdapter

        // Room Database 초기화
        val database = TripRoomDatabase.getDatabase(requireContext())
        tripDao = database.tripDao()

        // 데이터 로드
        loadTripsAndImages()

        searchButton.setOnClickListener {
            val keyword = searchText.text.toString().trim()
            searchTripsAndImages(keyword)
        }

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addButton -> {
                val intent = Intent(getActivity(), AddActivity::class.java)
                getActivity()?.startActivity(intent)
            }

            else -> {
            }
        }
    }

    private fun loadTripsAndImages() {
        lifecycleScope.launch {
            val trips = tripDao.getAllTrips()
            tripList.clear()

            for (trip in trips) {
                val images = tripDao.getImagesByTripId(trip.id.toLong())
                tripList.add(TripWithImages(trip, images))
            }

            tripAdapter.notifyDataSetChanged()
        }
    }

    private fun searchTripsAndImages(keyword: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val trips = if (keyword.isBlank()) {
                tripDao.getAllTrips()
            } else {
                tripDao.searchTrips(keyword)
            }

            val searchedTripsWithImages = mutableListOf<TripWithImages>()
            for (trip in trips) {
                val images = tripDao.getImagesByTripId(trip.id.toLong())
                searchedTripsWithImages.add(TripWithImages(trip, images))
            }

            withContext(Dispatchers.Main) {
                tripList.clear()
                tripList.addAll(searchedTripsWithImages)
                tripAdapter.notifyDataSetChanged()
            }
        }
    }
}