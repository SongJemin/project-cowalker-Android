package com.jemcom.cowalker.Nuri.Holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.jemcom.cowalker.R

class QuestionList2ViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView) {
    var questionTitle : EditText? = itemView.findViewById(R.id.question_item_ev)
}