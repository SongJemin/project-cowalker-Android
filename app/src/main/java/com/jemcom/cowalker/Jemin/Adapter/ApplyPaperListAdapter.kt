package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.R

class ApplyPaperListAdapter(private var answerListItems : ArrayList<String>, private var questionListItems : ArrayList<String>) : RecyclerView.Adapter<ApplyPaperListViewHolder>() {



    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyPaperListViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.apply_paper_item, parent, false)
        return ApplyPaperListViewHolder(mainView)
    }

    override fun getItemCount(): Int = answerListItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: ApplyPaperListViewHolder, position: Int) {
        holder.answerData.text = answerListItems[position]
        holder.questionData.text = questionListItems[position]
    }
}