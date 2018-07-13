package com.jemcom.cowalker.Jemin.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Jemin.Activity.*
import com.jemcom.cowalker.Network.Get.GetApplyMemberMessage
import com.jemcom.cowalker.Network.Get.Response.GetApplyMemberResponse
import com.jemcom.cowalker.R
import retrofit2.Callback

class ApplyMemberAdapter(context: Context, private var applyMemberItems: ArrayList<GetApplyMemberMessage>, var requestManager: RequestManager) : RecyclerView.Adapter<ApplyMemberViewHolder>() {

    val  mContext : Context = context
    var apply_idx : ArrayList<String> = ArrayList<String>()
    var applicant_idxList : ArrayList<Int> = ArrayList<Int>()
    var applicant_idx : String = ""
    var recruit_idx : String = ""
    var num : String = ""
    var task : String = ""
    var apply_idx_result : String = ""
    var token : String = ""

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyMemberViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.apply_member_item, parent, false)
//        mainView.setOnClickListener(onItemClick)
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
        apply_idx.add(applyMemberItems[position].apply_idx)
        applicant_idxList.add(applyMemberItems[position].applicant_idx)

        holder.applyMemberContentBtn.setOnClickListener {
            Log.v("Adapter", "클릭 postion = " + position)
            Log.v("Adapter", "지원 번호 = " + apply_idx[position])
            Log.v("Adapter", "지원자 번호 = " + applicant_idxList[position])

            recruit_idx = ApplyMemberActivity.applyMemberActivity.recruit_idx
            num = ApplyMemberActivity.applyMemberActivity.num
            task = ApplyMemberActivity.applyMemberActivity.task

            applicant_idx = applicant_idxList[position].toString()
            var intent = Intent(mContext, ApplyPaperActivity::class.java)
            intent.putExtra("apply_idx",apply_idx[position])
            intent.putExtra("applicant_idx",applicant_idx)
            intent.putExtra("recruit_idx",recruit_idx)
            intent.putExtra("num",num)
            intent.putExtra("task",task)
            intent.putExtra("position",applyMemberItems[position].position)
            Log.v("Adapter", "지원서로 보내는 모집 번호 = "+ recruit_idx)
            Log.v("Adapter", "지원서로 보내는 넘 = "+ num)
            Log.v("Adapter", "지원서로 보내는 태스크 = "+ task)
            mContext.startActivity(intent)

                    //mContext.startActivity(Intent(mContext, MainActivity::class.java)
                    //.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    //)
        }


        holder.applyMemberApproveBtn.setOnClickListener {
            Log.v("Adapter", "클릭 postion = " + position)
            Log.v("Adapter", "지원 번호 = " + apply_idx[position])
            Log.v("Adapter", "지원자 번호 = " + applicant_idxList[position])

            recruit_idx = ApplyMemberActivity.applyMemberActivity.recruit_idx
            token = ApplyMemberActivity.applyMemberActivity.token
            applicant_idx = applicant_idxList[position].toString()
            apply_idx_result = apply_idx[position]
            Log.v("TAG", "지원리스트에서 승인, 토큰 = " + token)
            Log.v("TAG", "지원리스트에서 승인, 지원자번호 = " + applicant_idx)
            Log.v("TAG", "지원리스트에서 승인, 지원번호 = " + apply_idx_result)
            var join = 1
            ApplyMemberActivity.applyMemberActivity.changeAdapterJoin(token, apply_idx_result, applicant_idx, join)
            var intent = Intent(mContext, ApplyMemberActivity::class.java)
            intent.putExtra("flag",2)
            mContext.startActivity(intent)

            Log.v("Adapter", "지원서로 보내는 모집 번호 = "+ recruit_idx)
        }

        holder.applyMemberRejectBtn.setOnClickListener {
            Log.v("Adapter", "클릭 postion = " + position)
            Log.v("Adapter", "지원 번호 = " + apply_idx[position])
            Log.v("Adapter", "지원자 번호 = " + applicant_idxList[position])

            recruit_idx = ApplyMemberActivity.applyMemberActivity.recruit_idx
            token = ApplyMemberActivity.applyMemberActivity.token
            applicant_idx = applicant_idxList[position].toString()
            apply_idx_result = apply_idx[position]
            Log.v("TAG", "지원리스트에서 거절, 토큰 = " + token)
            Log.v("TAG", "지원리스트에서 거절, 지원자번호 = " + applicant_idx)
            Log.v("TAG", "지원리스트에서 거절, 지원번호 = " + apply_idx_result)
            var join = 2
            ApplyMemberActivity.applyMemberActivity.changeAdapterJoin(token, apply_idx_result, applicant_idx, join)
            var intent = Intent(mContext, ApplyMemberActivity::class.java)
            intent.putExtra("flag",2)
            mContext.startActivity(intent)

            Log.v("Adapter", "지원서로 보내는 모집 번호 = "+ recruit_idx)

        }

    }


}