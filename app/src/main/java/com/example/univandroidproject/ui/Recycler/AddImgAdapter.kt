import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.ui.Recycler.ImgItem

class AddImgAdapter(private val ImgList : List<ImgItem>) :
    RecyclerView.Adapter<AddImgAdapter.ImgViewHolder>() {

    class ImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ImgView : ImageView = itemView.findViewById(R.id.imageView)
        val ImgText : TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.img_item_horizontal, parent, false)
        return ImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val Img = ImgList[position]
        holder.ImgView.setImageResource(Img.travelImage)
        holder.ImgText.text = Img.imgText
    }

    override fun getItemCount(): Int {
        return ImgList.size
    }

}