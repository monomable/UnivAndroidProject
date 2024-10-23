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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView : MapView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //return inflater.inflate(R.layout.fragment_map, container, false)
        //fragment_map과 연결시켜 return해줌.

        val rootView = inflater.inflate(R.layout.fragment_map,container,false)
        mapView = rootView.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(map: GoogleMap) {
        val point = LatLng(37.514655, 126.979974)
        map.addMarker(MarkerOptions().position(point).title("현위치"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,12f))

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    /* 버튼 작동부 - 버튼 삭제함
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mvbutton: Button = view.findViewById<Button>(R.id.NewButton)
        mvbutton.setOnClickListener{
            //val intent = Intent (getActivity(), AddActivity::class.java)
            //getActivity()?.startActivity(intent)
        }
    }*/
}