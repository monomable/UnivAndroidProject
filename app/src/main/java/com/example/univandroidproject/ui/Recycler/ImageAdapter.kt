import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.data.ImageEntity

class ImageAdapter(
    private val imageList: List<ImageEntity?>,
    private val onAddImageClick: () -> Unit,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ADD_ICON = 0
        private const val VIEW_TYPE_IMAGE = 1
    }

    inner class AddIconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addIcon: ImageView = itemView.findViewById(R.id.addIcon)

        init {
            addIcon.setOnClickListener {
                onAddImageClick()
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            imageView.setOnClickListener {
                onImageClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ADD_ICON) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_icon, parent, false)
            AddIconViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            ImageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            val imageEntity = imageList[position]!!
            val directory = holder.itemView.context.filesDir
            val filePath = "$directory/${imageEntity.imageKey}.png"
            val bitmap = BitmapFactory.decodeFile(filePath)
            holder.imageView.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int = imageList.size

    override fun getItemViewType(position: Int): Int {
        return if (imageList[position] == null) VIEW_TYPE_ADD_ICON else VIEW_TYPE_IMAGE
    }
}