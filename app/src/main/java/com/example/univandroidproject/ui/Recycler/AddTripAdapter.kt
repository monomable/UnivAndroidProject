import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.databinding.ImgItemHorizontalBinding

class AddTripAdapter(
    private val data: List<String>,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<AddTripAdapter.ViewHolder>() {

    private val images = MutableList<Bitmap?>(data.size) { null }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.img_item_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], images[position])
        holder.itemView.setOnClickListener { itemClickListener(position) }
    }

    override fun getItemCount() = data.size

    fun updateImage(position: Int, bitmap: Bitmap) {
        images[position] = bitmap
        notifyItemChanged(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(text: String, image: Bitmap?) {
            imageView.setImageBitmap(image ?: BitmapFactory.decodeResource(itemView.resources, R.drawable.plusicon))
        }
    }
}
