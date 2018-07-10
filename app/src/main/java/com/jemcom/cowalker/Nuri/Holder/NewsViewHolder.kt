package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var newsName : TextView = itemView.findViewById(R.id.newsitem_name_tv)
    var newsDate : TextView = itemView.findViewById(R.id.newsitem_date_tv)
    var newsPreview : TextView = itemView.findViewById(R.id.newsitem_preview_tv)
}