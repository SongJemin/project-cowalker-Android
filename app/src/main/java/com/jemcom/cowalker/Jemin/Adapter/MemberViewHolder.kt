package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class MemberViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView) {
    var memberNumber : TextView = itemView.findViewById(R.id.member_name_tv)
    var memberPosition : TextView = itemView.findViewById(R.id.member_position_tv);
    var memberProfileImage : ImageView = itemView.findViewById(R.id.memberitem_profile_img)

}