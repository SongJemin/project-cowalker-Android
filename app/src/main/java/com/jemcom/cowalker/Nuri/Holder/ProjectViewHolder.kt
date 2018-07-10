package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class ProjectViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var img : ImageView = itemView.findViewById(R.id.projectlist_iv)
    var title : TextView = itemView.findViewById(R.id.projectlist_title_tv)
    var sub : TextView = itemView.findViewById(R.id.projectlist_sub_tv)
}