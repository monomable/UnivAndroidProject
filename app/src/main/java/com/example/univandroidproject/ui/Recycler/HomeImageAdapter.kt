package com.example.univandroidproject.ui.Recycler

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.data.ImageEntity

class HomeImageAdapter(
    private val imageList: List<ImageEntity>
) : RecyclerView.Adapter<HomeImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageEntity = imageList[position]
        val directory = holder.itemView.context.filesDir
        val filePath = "$directory/${imageEntity.imageKey}.png"
        val bitmap = BitmapFactory.decodeFile(filePath)
        holder.imageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int = imageList.size
}