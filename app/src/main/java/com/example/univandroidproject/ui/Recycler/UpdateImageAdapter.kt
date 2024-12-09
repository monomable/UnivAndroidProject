package com.example.univandroidproject.ui.Recycler

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.univandroidproject.R
import com.example.univandroidproject.data.ImageEntity
import com.example.univandroidproject.data.TripRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UpdateImageAdapter(
    private val imageList: List<ImageEntity>
) : RecyclerView.Adapter<UpdateImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
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