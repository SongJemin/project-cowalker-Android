package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.jemcom.cowalker.R

class QuestionListViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView) {
    var questionTitle : TextView = itemView.findViewById(R.id.question_item_tv)
    var questionEdit : EditText = itemView.findViewById(R.id.question_ltem_edit)
}