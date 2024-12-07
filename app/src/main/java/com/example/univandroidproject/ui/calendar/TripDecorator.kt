package com.example.univandroidproject.ui.calendar

import android.graphics.Color
import android.graphics.Typeface
import com.google.android.gms.maps.model.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TripDecorator(private val dates: List<CalendarDay>) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(android.text.style.ForegroundColorSpan(Color.RED)) // 강조 표시 색상
    }
}