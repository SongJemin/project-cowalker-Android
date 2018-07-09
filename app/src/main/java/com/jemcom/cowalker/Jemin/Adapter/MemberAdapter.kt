package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.Get.GetProjectMemberMessage
import com.jemcom.cowalker.R

class MemberAdapter(private var memberItems : ArrayList<GetProjectMemberMessage>, var requestManager : RequestManager) : RecyclerView.Adapter<MemberViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.member_list_item, parent, false)
//        mainView.setOnClickListener(onItemClick)
        return MemberViewHolder(mainView)
    }

    override fun getItemCount(): Int = memberItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        requestManager.load(memberItems[position].profile_url).into(holder.memberProfileImage)
        // default값 (서버에서 이름 넘겨주는 걸로 바뀌면 수정)
        //holder.memberNumber.text = memberItems[position].member_idx.toString()
        holder.memberNumber.text = "송제민"
        holder.memberPosition.text = memberItems[position].position
    }
}