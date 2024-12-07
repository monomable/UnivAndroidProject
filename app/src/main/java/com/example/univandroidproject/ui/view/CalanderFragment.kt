package com.example.univandroidproject.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.univandroidproject.R
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.ui.calendar.TripDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Locale

class CalanderFragment : Fragment() {

    private lateinit var calendarView: MaterialCalendarView
    private lateinit var database: TripRoomDatabase

    // 날짜 포맷 설정 ("yy-MM-dd")
    private val dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calander, container, false)
        calendarView = view.findViewById(R.id.calendar_view)

        // 캘린더 상단에 년도,월 표시
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)

        // Room 데이터베이스 초기화
        database = TripRoomDatabase.getDatabase(requireContext())

        // 캘린더 설정
        setupCalendar()

        return view
    }

    private fun setupCalendar() {
        lifecycleScope.launch {
            val trips = withContext(Dispatchers.IO) { database.tripDao().getAllTrips() }

            // Room 데이터베이스에서 읽어온 날짜를 CalendarDay로 변환
            val highlightedDays = trips.mapNotNull { trip ->
                runCatching {
                    LocalDate.parse(trip.tripStartDay, dateFormatter).let { date ->
                        CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
                    }
                }.getOrNull()
            }

            // 강조 표시된 날짜를 캘린더에 추가
            calendarView.addDecorators(TripDecorator(highlightedDays))
        }
    }
}