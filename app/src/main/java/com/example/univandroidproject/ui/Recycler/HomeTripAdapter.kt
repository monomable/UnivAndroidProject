import android.content.Intent
import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.UpdateActivity
import com.example.univandroidproject.data.Trip
import com.example.univandroidproject.databinding.FragmentHomeBinding

class HomeTripAdapter : RecyclerView.Adapter<HomeTripAdapter.ViewHolder>() {

    private var tripList = emptyList<Trip>()

    class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView){
        /*val thumbnail : ImageView = itemView.findViewById(R.id.item_thumbnail)
        val title : TextView = itemView.findViewById(R.id.item_title)
        val content : TextView = itemView.findViewById(R.id.item_contents)
        val startDay : TextView = itemView.findViewById(R.id.item_start_date)
        val endDay :  TextView = itemView.findViewById(R.id.item_end_date)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false))
        // item_list.xml 레이아웃과 연결
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = tripList[position]
        holder.itemView.findViewById<TextView>(R.id.item_title).text = currentItem.tripTitle
        holder.itemView.findViewById<TextView>(R.id.item_contents).text = currentItem.tripContents
        holder.itemView.findViewById<TextView>(R.id.item_start_date).text = currentItem.tripStartDay
        holder.itemView.findViewById<TextView>(R.id.item_end_date).text = currentItem.tripEndDay

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, UpdateActivity::class.java).apply {
                // 데이터를 Intent에 추가
                putExtra("tripTitle", currentItem.tripTitle)
                putExtra("tripContents", currentItem.tripContents)
                putExtra("tripStartDay", currentItem.tripStartDay)
                putExtra("tripEndDay", currentItem.tripEndDay)
            }
            context.startActivity(intent)
        }
        //val Img = ImgList[position]
        //holder.ImgView.setImageBitmap(Img.tripImage)
        //holder.ImgText.text = Img.imgText
    }

    fun setData(trip:List<Trip>){
        this.tripList = trip
        notifyDataSetChanged()
    }


}