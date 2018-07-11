package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class ApplyMemberViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView) {
    var applyMemberName : TextView = itemView.findViewById(R.id.apply_member_name_tv)
    var applyMemberPosition : TextView = itemView.findViewById(R.id.apply_member_position_tv);
    var applyMemberProfileImage : ImageView = itemView.findViewById(R.id.apply_member_profile_img)

}