package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.Get.GetApplyMemberMessage
import com.jemcom.cowalker.Network.Get.GetProjectMemberMessage
import com.jemcom.cowalker.R

class ApplyMemberAdapter(private var applyMemberItems : ArrayList<GetApplyMemberMessage>, var requestManager : RequestManager) : RecyclerView.Adapter<ApplyMemberViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(I : View.OnClickListener)
    {
        onItemClick = I
    }

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyMemberViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.apply_member_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ApplyMemberViewHolder(mainView)
    }

    override fun getItemCount(): Int = applyMemberItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: ApplyMemberViewHolder, position: Int) {
        requestManager.load(applyMemberItems[position].profile_url).into(holder.applyMemberProfileImage)
        // default값 (서버에서 이름 넘겨주는 걸로 바뀌면 수정)
        //holder.memberNumber.text = memberItems[position].member_idx.toString()
        holder.applyMemberName.text = applyMemberItems[position].user_name
        holder.applyMemberPosition.text = applyMemberItems[position].position
    }
}