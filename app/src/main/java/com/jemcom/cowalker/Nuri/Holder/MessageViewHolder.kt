package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class MessageViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var messageProfile : ImageView = itemView.findViewById(R.id.messageitem_profile_cv)
    var messageName : TextView = itemView.findViewById(R.id.messageitem_name_tv)
    var messageDate : TextView = itemView.findViewById(R.id.messageitem_date_tv)
    var messagePreview : TextView = itemView.findViewById(R.id.messageitem_preview_tv)
}