package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Nuri.Activity.ApplyModify4Activity
import com.jemcom.cowalker.Nuri.Holder.QuestionList2ViewHolder
import com.jemcom.cowalker.R

class QuestionList2Adapter (private var questionListItems : ArrayList<String>) : RecyclerView.Adapter<QuestionList2ViewHolder>() {



    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionList2ViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_list_item2, parent, false)
        return QuestionList2ViewHolder(mainView)
    }

    override fun getItemCount(): Int = questionListItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: QuestionList2ViewHolder, position: Int) {
        holder.questionNum.setText((position+1).toString())
        holder.questionTitle!!.setText(questionListItems[position])
        holder.questionTitle!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                if(ApplyModify4Activity.Oactivity.question_list.size > position)
                {
                    ApplyModify4Activity.Oactivity.question_list[position] = holder.questionTitle!!.text.toString()
                }
                else ApplyModify4Activity.Oactivity.question_list.add(holder.questionTitle!!.text.toString())

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}