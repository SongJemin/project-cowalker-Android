package com.jemcom.cowalker.Nuri.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitDetail
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_invite.*
import kotlinx.android.synthetic.main.activity_invite2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyModify2Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var data : ArrayList<GetRecruitDetail>
    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            invite2_next_btn -> {
                var intent = Intent(applicationContext,ApplyModify3Activity::class.java)
                intent.putExtra("position",getIntent().getStringExtra("position"))
                intent.putExtra("number",getIntent().getStringExtra("number"))
                intent.putExtra("start_date",getIntent().getStringExtra("start_date"))
                intent.putExtra("end_date,",getIntent().getStringExtra("end_date"))
                intent.putExtra("task",invite2_task_edit.text)
                intent.putExtra("activity",invite2_activity_edit.text)
                intent.putExtra("area",invite2_area_edit.text)
                intent.putExtra("reward",invite2_reward_edit.text)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite2)

        networkService = ApplicationController.instance.networkSerVice
        invite2_next_btn.setOnClickListener(this)

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
                    data = response.body().result
                    invite2_task_edit.setText(data[0].task)
                    invite2_activity_edit.setText(data[0].activity)
                    invite2_area_edit.setText(data[0].area)
                    invite2_reward_edit.setText(data[0].reward)
                }
                else Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}