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
import java.io.File

class UpdateImageAdapter(
    private val imageList: List<ImageEntity>, // 데이터베이스에서 가져온 ImageEntity 목록
    private val context: Context // 로컬 저장소 접근을 위한 Context
) : RecyclerView.Adapter<UpdateImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageEntity = imageList[position]
        val imageKey = imageEntity.imageKey
        val directory = context.filesDir
        val filePath = "$directory/$imageKey.png"

        val file = File(filePath)
        if (file.exists()) {
            // 로컬 파일에서 Bitmap을 로드하고 ImageView에 표시
            val bitmap = BitmapFactory.decodeFile(filePath)
            holder.imageView.setImageBitmap(bitmap)
        } else {
            // 파일이 없을 경우 오류 이미지 표시
            holder.imageView.setImageResource(R.drawable.baseline_settings_24)
        }
    }

    override fun getItemCount(): Int = imageList.size
}