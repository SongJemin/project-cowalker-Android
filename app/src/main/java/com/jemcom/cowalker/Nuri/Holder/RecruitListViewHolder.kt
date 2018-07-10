package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class RecruitListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var check : ImageView = itemView.findViewById(R.id.recruit_delete_check)
    var postion : TextView = itemView.findViewById(R.id.recruit_list_position_tv)
    var number : TextView = itemView.findViewById(R.id.recruit_list_num_tv)
    var task : TextView = itemView.findViewById(R.id.recruit_list_task_tv)
    var dday : TextView = itemView.findViewById(R.id.recruit_list_dday_tv)
}