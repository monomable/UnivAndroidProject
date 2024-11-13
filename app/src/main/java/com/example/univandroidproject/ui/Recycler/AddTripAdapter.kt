import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.db.Trip

class AddTripAdapter(var context: Context, var list:List<Trip>, var recyclerVeiwLayoutType : Int, val itemClickListener:(Trip)->Unit) : RecyclerView.Adapter<AddTripAdapter.Holder>() {
    inner class Holder(itemView: View,val itemClickListener:(Trip)->Unit) : RecyclerView.ViewHolder(itemView){

        var thumbnail : ImageView = itemView.findViewById(R.id.item_thumbnail)
        var title : TextView = itemView.findViewById(R.id.item_title)
        var contents : TextView = itemView.findViewById(R.id.item_contents)
        var start_date : TextView = itemView.findViewById(R.id.item_start_date)
        var end_date : TextView = itemView.findViewById(R.id.item_end_date)

        fun bindData(context: Context, trip: Trip){
            thumbnail.setImageResource(trip.thumbnail)
            title.text = trip.title
            contents.text = trip.contents
            start_date.text = trip.start_day.toString()
            end_date.text = trip.end_day.toString()

            itemView.setOnClickListener{
                itemClickListener(trip)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)

        if (recyclerVeiwLayoutType==2){
            view = LayoutInflater.from(context).inflate(R.layout.item_list_grid,parent,false)
        }

        return Holder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(context,list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}