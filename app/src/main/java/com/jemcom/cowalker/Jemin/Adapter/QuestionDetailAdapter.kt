package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Jemin.Activity.ApplyActivity
import com.jemcom.cowalker.Jemin.Activity.ApplyDetailActivity
import com.jemcom.cowalker.Jemin.Item.ProjectItem
import com.jemcom.cowalker.Jemin.Item.QuestionListItem
import com.jemcom.cowalker.R

class QuestionDetailAdapter(private var questionDetailItems : ArrayList<String>) : RecyclerView.Adapter<QuestionDetailViewHolder>() {



    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionDetailViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_list_item, parent, false)
        return QuestionDetailViewHolder(mainView)
    }

    override fun getItemCount(): Int = questionDetailItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: QuestionDetailViewHolder, position: Int) {
        holder.questionTitle.text = questionDetailItems[position]
        holder.questionEdit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

                if(ApplyDetailActivity.detailActivity.detailAnswerList.size > position)
                {
                    ApplyDetailActivity.detailActivity.detailAnswerList[position] = holder.questionEdit.text.toString()
                }
                else ApplyDetailActivity.detailActivity.detailAnswerList.add(holder.questionEdit.text.toString())

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}