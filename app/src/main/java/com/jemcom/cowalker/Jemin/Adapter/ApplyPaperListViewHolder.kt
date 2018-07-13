package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.jemcom.cowalker.R

class ApplyPaperListViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    var answerData : TextView = itemView.findViewById(R.id.apply_paper_answer)
    var questionData : TextView = itemView.findViewById(R.id.apply_paper_question)
}