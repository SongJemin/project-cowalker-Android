package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Nuri.Activity.RecommendActivity
import com.jemcom.cowalker.Nuri.Holder.RecommendViewHolder
import com.jemcom.cowalker.Nuri.Item.RecommendItem
import com.jemcom.cowalker.R

class RecommendAdapter (private var recommendItems : ArrayList<RecommendItem>) : RecyclerView.Adapter<RecommendViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recommend_item, parent, false)
        return RecommendViewHolder(mainView)
    }

    override fun getItemCount(): Int = recommendItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.name.text = recommendItems[position].name
        holder.btn.setOnClickListener {
                if (holder.btn.isSelected == true) {
                    RecommendActivity.activity.check = 0
                    RecommendActivity.activity.position = -1
                    holder.btn.isSelected = false
                } else {
                    if(RecommendActivity.activity.check == 0)
                    {
                        RecommendActivity.activity.position = position
                        RecommendActivity.activity.check = 1
                        if(position== 0) RecommendActivity.activity.idx = ""
                        else RecommendActivity.activity.idx = recommendItems[position].idx
                        holder.btn.isSelected = true
                    }
                }
        }
    }
}