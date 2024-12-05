import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.UpdateActivity
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.data.TripWithImages
import com.example.univandroidproject.ui.Recycler.HomeImageAdapter

class HomeTripAdapter(private val tripList: List<TripWithImages>) : RecyclerView.Adapter<HomeTripAdapter.ViewHolder>() {

    //private var tripList = emptyList<Trip>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tripTitle)
        val contents: TextView = itemView.findViewById(R.id.tripContents)
        val startday: TextView = itemView.findViewById(R.id.item_start_date)
        val endday: TextView = itemView.findViewById(R.id.item_end_date)
        val tag: TextView = itemView.findViewById(R.id.tripTag)
        val imageRecyclerView: RecyclerView = itemView.findViewById(R.id.imageRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return ViewHolder(view)
        // item_list.xml 레이아웃과 연결
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tripWithImages = tripList[position]
        val trip = tripWithImages.trip
        val images = tripWithImages.images

        holder.title.text = trip.tripTitle
        holder.contents.text = trip.tripContents
        holder.startday.text = trip.tripStartDay
        holder.endday.text = trip.tripEndDay
        holder.tag.text = trip.tripTag

        val imageAdapter = HomeImageAdapter(images)
        holder.imageRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.imageRecyclerView.adapter = imageAdapter


        holder.itemView.setOnClickListener{
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

    override fun getItemCount(): Int = tripList.size


    /*fun setData(trip:List<Trip>){
        this.tripList = trip
        notifyDataSetChanged()
    }*/


}