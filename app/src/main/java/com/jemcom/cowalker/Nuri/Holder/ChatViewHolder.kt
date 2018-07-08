package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jemcom.cowalker.R

class ChatViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var chatName : TextView = itemView.findViewById(R.id.chatitem_name_tv)
    var chatDate : TextView = itemView.findViewById(R.id.chatitem_date_tv)
    var chatPreview : TextView = itemView.findViewById(R.id.chatitem_preview_tv)
}