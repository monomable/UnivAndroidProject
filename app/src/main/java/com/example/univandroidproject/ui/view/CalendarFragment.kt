package com.example.univandroidproject.ui.view

import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripRoomDatabase
import com.example.univandroidproject.ui.Recycler.CalendarTripAdapter
import com.example.univandroidproject.ui.calendar.TripDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var calendarView: MaterialCalendarView
    private lateinit var database: TripRoomDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var tripAdapter: CalendarTripAdapter
    private lateinit var selectedDateText: TextView

    // 날짜 포맷 설정 ("yy-MM-dd")
    private val dateFormatter = DateTimeFormatter.ofPattern("yy/MM/dd")
    private val displayDateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calander, container, false)
        calendarView = view.findViewById(R.id.calendar_view)
        recyclerView = view.findViewById(R.id.calendar_recyclerview)
        selectedDateText = view.findViewById(R.id.select_date)

        var selectedMonthDecorator = SelectedMonthDecorator(CalendarDay.today().month)

        // 캘린더 상단에 년도,월 표시
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        // 현재 달에 보이는 이전, 다음달 날짜 회색으로 표시
        calendarView.addDecorator(selectedMonthDecorator)

        // Room 데이터베이스 초기화
        database = TripRoomDatabase.getDatabase(requireContext())

        // 캘린더 내부에 Trip start Day 색상 변경
        setupCalendar()
        setupRecyclerView()

        // 현재달에 보이는 이전달 다음달 회색으로 변경
        calendarView.setOnMonthChangedListener { widget, date ->
            calendarView.removeDecorators()
            calendarView.invalidateDecorators()

            setupCalendar()

            selectedMonthDecorator = SelectedMonthDecorator(date.month)
            calendarView.addDecorators(selectedMonthDecorator)
        }

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

        calendarView.setOnDateChangedListener { _, date, _ ->
            val selectedDate = LocalDate.of(date.year, date.month, date.day)
            val formattedDate = dateFormatter.format(selectedDate)

            // Update the selected date TextView
            selectedDateText.text = displayDateFormatter.format(selectedDate)

            // Fetch trips for the selected date
            fetchTripsForDate(formattedDate)
        }
    }

    private fun setupRecyclerView() {
        tripAdapter = CalendarTripAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = tripAdapter
    }

    private inner class SelectedMonthDecorator(val selectedMonth : Int) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day.month != selectedMonth
        }
        override fun decorate(view: DayViewFacade) {
            view.addSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.light_gray)))
        }
    }

    private fun fetchTripsForDate(date: String) {
        lifecycleScope.launch {
            val trips = withContext(Dispatchers.IO) {
                database.tripDao().getTripsByDate(date)
            }
            updateRecyclerView(trips)
        }
    }

    private fun updateRecyclerView(trips: List<Trip>) {
        tripAdapter.updateTrips(trips)
    }
    /*
    private inner class  ChangeMonthDecorator() {
        calendarView
    }*/
}