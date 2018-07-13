package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class RecommendViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name : TextView = itemView.findViewById(R.id.recommend_tv)
    var btn : ImageView = itemView.findViewById(R.id.recommend_iv)
}