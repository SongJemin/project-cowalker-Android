package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.ApplyDetailActivity
import com.jemcom.cowalker.Jemin.Activity.ApplyMemberActivity
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.Jemin.Activity.ProjectMemberActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_recruit_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class RecruitDetailActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    var project_idx : String = ""
    var recruit_idx : String = ""
    var position : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_detail)

        networkService = ApplicationController.instance.networkSerVice
        val getRecruitintent = intent
        project_idx = getRecruitintent.getStringExtra("project_idx")
        recruit_idx = getRecruitintent.getStringExtra("recruit_idx")
        get()

        recruit_detail_applymember_linear.setOnClickListener{
            var intent = Intent(applicationContext, ApplyMemberActivity::class.java)
            intent.putExtra("recruit_idx",recruit_idx)
            startActivity(intent)
        }

        recruit_detail_participmember_linear.setOnClickListener{
            var intent = Intent(applicationContext, ProjectMemberActivity::class.java)
            intent.putExtra("project_idx",project_idx)
            startActivity(intent)
        }

        recruit_detail_btn.setOnClickListener {
            var intent = Intent(applicationContext, ApplyDetailActivity::class.java)
            intent.putExtra("project_idx",project_idx)
            intent.putExtra("recruit_idx",recruit_idx)
            intent.putExtra("recruit_idx",recruit_idx)
            intent.putExtra("position",position)
            Log.v("TAG","리쿠릇에서 어플라이로 보내는 포지션 = " + position)

            startActivity(intent)
        }

    }

    fun get()
    {

        Log.v("TAG","모집 세부사항 모집번호= " + recruit_idx)
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        //val project_idx = "5b3dd2387172d402215033d2"
        //val recruit_idx = "5b3ecc11ca5c3444e4f802f1"
        val getRecruitDetailResponse = networkService.getRecruitDetail("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMTcsImlhdCI6MTUzMTE3MzIzOSwiZXhwIjoxNTMzNzY1MjM5fQ.taqF_rP7P2DzGiSTT234wv3dqjjsTBLA0J01K-PDlxk",project_idx,recruit_idx)

        getRecruitDetailResponse.enqueue(object : Callback<GetRecruitDetailResponse>{
            override fun onFailure(call: Call<GetRecruitDetailResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitDetailResponse>?, response: Response<GetRecruitDetailResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    var btnResult = response.body().btnResult
//                    var sdf = SimpleDateFormat("yyyy.MM.dd")
//                    var start = sdf.format("2018-07-03T00:00:00.000Z")
//                    var end = sdf.format("2018-07-20T00:00:00.000Z")
//                    var date = start + " ~ " + end
                    position = data[0].position
                    recruit_detail_position_tv.setText(data[0].position)
//                    recruit_detail_date_tv.setText(date)
                    recruit_detail_num_tv.setText(data[0].number)
                    recruit_detail_task_tv.setText(data[0].task)
                    recruit_detail_activity_tv.setText(data[0].activity)
//                    recruit_detail_time_tv.setText(date)
                    recruit_detail_area_tv.setText(data[0].area)
                    recruit_detail_reward_tv.setText(data[0].reward)
                    recruit_detail_ability_tv.setText(data[0].ability)
                    recruit_detail_career_tv.setText(data[0].career)
                    recruit_detail_preference_tv.setText(data[0].preference)

                    //나중에 버튼 텍스트 변경 예정
                    //recruit_detail_comment_tv.setText(data[0].comment)

                    recruit_detail_btn.setText(btnResult)
                    if(btnResult.equals("참여완료"))
                    {
                        recruit_detail_btn.setBackgroundColor(Color.parseColor("#eeeeee"))
                        recruit_detail_btn.setTextColor(Color.parseColor("#c5c5c5"))
                    }
                    if(!btnResult.equals("개발자"))
                    {
                        //recruit_detail_recommend.setVisibility(View.GONE)
                    }
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
