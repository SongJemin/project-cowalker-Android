package com.jemcom.cowalker.Nuri.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_invite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyModifyActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            invite1_next_btn -> {
                var intent = Intent(applicationContext,ApplyModify2Activity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite)

        networkService = ApplicationController.instance.networkSerVice
        invite1_next_btn.setOnClickListener(this)

        get()
    }

    fun get()
    {

        val recruit_idx = "5b3ecc11ca5c3444e4f802f1"
        val project_idx = "5b3dd2387172d402215033d2"
        val getRecruitDetailResponse = networkService.getRecruitDetail("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA2NzAxNTMsImV4cCI6MTUzMzI2MjE1M30.BdRb0yary7AY8_yi8MDRDXuXrW19QSqRJI-9Xin3SXs",project_idx,recruit_idx)

        getRecruitDetailResponse.enqueue(object : Callback<GetRecruitDetailResponse> {
            override fun onFailure(call: Call<GetRecruitDetailResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitDetailResponse>?, response: Response<GetRecruitDetailResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    if(data[0].position.equals("PM")) invite_pm_btn.isSelected = true
                    else if(data[0].position.equals("기획자")) invite_planner_btn.isSelected = true
                    else if(data[0].position.equals("디자이너")) invite_designer_btn.isSelected = true
                    else if(data[0].position.equals("개발자")) invite_developer_btn.isSelected = true
                    else if(data[0].position.equals("기타")) invite_role_tv.isSelected = true
                    invite_personnel_edit.setText(data[0].number)
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}