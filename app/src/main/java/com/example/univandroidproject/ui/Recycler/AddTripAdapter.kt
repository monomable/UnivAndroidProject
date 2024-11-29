import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.databinding.ImgItemHorizontalBinding

class AddTripAdapter(
    private val data: List<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<AddTripAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ImgItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.textView.text = item
            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImgItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}