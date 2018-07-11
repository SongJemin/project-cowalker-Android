package com.jemcom.cowalker.Jemin.Activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Jemin.Adapter.ApplyMemberAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetApplyMemberMessage
import com.jemcom.cowalker.Network.Get.Response.GetApplyMemberResponse
import com.jemcom.cowalker.Network.NetworkService

import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_apply_member.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyMemberActivity : AppCompatActivity(), View.OnClickListener {

    private val context: Context = this

    override fun onClick(v: View?) {
        Log.v("TAG","지원자 멤버 클릭")
    }

    var recruit_idx: String = ""
    var applyMemberItems : ArrayList<GetApplyMemberMessage> = ArrayList()
    lateinit var applyMemberData : ArrayList<GetApplyMemberMessage>
    lateinit var networkService : NetworkService
    var applyMemberNumber : Int = 0;
    var applyMemberPosition : String= "";
    var applyMemberProfileUrl : String= "";
    lateinit var requestManager : RequestManager // 이미지를 불러올 때 처리하는 변수
    lateinit var applyMemberAdapter : ApplyMemberAdapter

    var applicant_idx_result : Int = 0
    var applicant_idx : String = ""
    var apply_idx : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_member)
        applyMemberActivity = this
        val intent = intent
        recruit_idx = intent.getStringExtra("recruit_idx")
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)
        getMember()
    }

    companion object {
        lateinit var applyMemberActivity: ApplyMemberActivity
        //일종의 스태틱
    }

    fun getMember()
    {
        var getApplyMemberResponse = networkService.getApplyMemberList("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMTcsImlhdCI6MTUzMTE3MzIzOSwiZXhwIjoxNTMzNzY1MjM5fQ.taqF_rP7P2DzGiSTT234wv3dqjjsTBLA0J01K-PDlxk" ,recruit_idx) // 네트워크 서비스의 getContent 함수를 받아옴
        getApplyMemberResponse.enqueue(object : Callback<GetApplyMemberResponse> {
            override fun onResponse(call: Call<GetApplyMemberResponse>?, response: Response<GetApplyMemberResponse>?) {
                Log.v("TAG","지원 멤버 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","지원 멤버값 갖고오기 성공")
                    applyMemberData = response.body().result


                    applyMemberAdapter = ApplyMemberAdapter(this@ApplyMemberActivity, applyMemberData,requestManager)
                    apply_member_list_recyclerview.layoutManager = LinearLayoutManager( this@ApplyMemberActivity)
                    apply_member_list_recyclerview.adapter = applyMemberAdapter

                }


            }

            override fun onFailure(call: Call<GetApplyMemberResponse>?, t: Throwable?) {
                Log.v("TAG","지원멤버 통신 실패")
            }

        })
    }
}
