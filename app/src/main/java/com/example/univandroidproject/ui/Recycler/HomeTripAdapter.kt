import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.data.Trip

class HomeTripAdapter (private val ImgList : List<Trip>) :
    RecyclerView.Adapter<HomeTripAdapter.ImgViewHolder>() {

    var onItemClick: ((Trip) -> Unit)? = null

    class ImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ImgView : ImageView = itemView.findViewById(R.id.item_thumbnail)
        val ImgText : TextView = itemView.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val Img = ImgList[position]
        //holder.ImgView.setImageBitmap(Img.tripImage)
        //holder.ImgText.text = Img.imgText
    }

    override fun getItemCount(): Int {
        return ImgList.size
    }

}