package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitDetail
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.ApplyModify3Activity
import com.jemcom.cowalker.R
import com.jemcom.cowalker.R.id.*
import kotlinx.android.synthetic.main.activity_invite2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyModify2Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var data : ArrayList<GetRecruitDetail>
    lateinit var networkService: NetworkService
    var project_idx : String = ""
    var recruit_idx : String = ""

    override fun onClick(v: View?) {
        when(v)
        {
            invite2_next_btn -> {
                var intent = Intent(applicationContext, ApplyModify3Activity::class.java)
                intent.putExtra("position",getIntent().getStringExtra("position"))
                intent.putExtra("number",getIntent().getIntExtra("number",0))
                intent.putExtra("start_date",getIntent().getStringExtra("start_date"))
                intent.putExtra("end_date",getIntent().getStringExtra("end_date"))
                intent.putExtra("task",invite2_task_edit.text.toString())
                intent.putExtra("activity",invite2_activity_edit.text.toString())
                intent.putExtra("area",invite2_area_edit.text.toString())
                intent.putExtra("reward",invite2_reward_edit.text.toString())

                intent.putExtra("project_idx", project_idx)
                intent.putExtra("recruit_idx", recruit_idx)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite2)
        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            window.statusBarColor = Color.BLACK
        }
        networkService = ApplicationController.instance.networkSerVice
        invite2_next_btn.setOnClickListener(this)
        project_idx = intent.getStringExtra("project_idx")
        recruit_idx = intent.getStringExtra("recruit_idx")

        get()
    }

    fun get()
    {
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val getRecruitDetailResponse = networkService.getRecruitDetail(token,project_idx,recruit_idx)

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