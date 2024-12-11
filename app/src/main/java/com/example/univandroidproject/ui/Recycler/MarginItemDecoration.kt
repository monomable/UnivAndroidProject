package com.example.univandroidproject.ui.Recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // 현재 아이템의 위치
        val itemCount = parent.adapter?.itemCount ?: 0      // 전체 아이템 수

        with(outRect) {
            // 첫 번째 아이템의 왼쪽 마진 처리
            if (position == 0) {
                left = spaceSize
            } else {
                left = spaceSize / 2
            }

            // 마지막 아이템의 오른쪽 마진 처리
            if (position == itemCount - 1) {
                right = spaceSize
            } else {
                right = spaceSize / 2
            }

            // 모든 아이템의 상하 마진 처리
            top = spaceSize
            bottom = spaceSize
        }
    }
}