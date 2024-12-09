package com.example.univandroidproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.univandroidproject.databinding.ActivityMainBinding
import com.example.univandroidproject.ui.view.CalendarFragment
import com.example.univandroidproject.ui.view.FavoriteFragment
import com.example.univandroidproject.ui.view.HomeFragment
import com.example.univandroidproject.ui.view.MapFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈 프래그먼트 화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_home
        }
    }

    // 하단바 메뉴 설정
    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment()).commit()
                    true
                }
                R.id.fragment_calander -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, CalendarFragment()).commit()
                    true
                }
                R.id.fragment_favorite -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, FavoriteFragment()).commit()
                    true
                }
                R.id.fragment_map -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, MapFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

}