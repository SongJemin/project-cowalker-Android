package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.list_item.view.*

class ProjectViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView) {
    var projectImage : ImageView = itemView.findViewById(R.id.item_imgurl_img)
    var projectTitle : TextView = itemView.findViewById(R.id.item_title_tv)
    var projectArea : TextView = itemView.findViewById(R.id.item_area_tv);
    var projectDepartment : TextView = itemView.findViewById(R.id.item_department_tv);
    var projectAim : TextView = itemView.findViewById(R.id.item_aim_tv);
}