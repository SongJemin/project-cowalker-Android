package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jemcom.cowalker.R

class RecruitListViewHolder (itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var postion : TextView = itemView!!.findViewById(R.id.recruitlist_position_tv)
    var number : TextView = itemView!!.findViewById(R.id.recruitlist_number_tv)
    var task : TextView = itemView!!.findViewById(R.id.recruitlist_task_tv)
    var dday : TextView = itemView!!.findViewById(R.id.recruitlist_dday_tv)
}