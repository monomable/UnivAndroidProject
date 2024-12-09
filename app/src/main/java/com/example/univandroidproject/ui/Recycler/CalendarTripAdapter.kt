package com.example.univandroidproject.ui.Recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.UpdateActivity
import com.example.univandroidproject.data.Trip

class CalendarTripAdapter(private var trips: List<Trip>) : RecyclerView.Adapter<CalendarTripAdapter.TripViewHolder>() {

    fun updateTrips(newTrips: List<Trip>) {
        trips = newTrips
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.title.text = trip.tripTitle
        holder.startday.text = trip.tripStartDay
        holder.endday.text = trip.tripEndDay

        holder.itemView.setOnClickListener{ //HomeTripAdapter 와 동일한 클릭 리스너
            val context = holder.itemView.context
            val intent = Intent(context, UpdateActivity::class.java).apply {
                // 데이터를 Intent에 추가
                putExtra("tripTitle", trip.tripTitle)
                putExtra("tripContents", trip.tripContents)
                putExtra("tripStartDay", trip.tripStartDay)
                putExtra("tripEndDay", trip.tripEndDay)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = trips.size

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tripTitle)
        val startday: TextView = itemView.findViewById(R.id.item_start_date)
        val endday: TextView = itemView.findViewById(R.id.item_end_date)
    }
}