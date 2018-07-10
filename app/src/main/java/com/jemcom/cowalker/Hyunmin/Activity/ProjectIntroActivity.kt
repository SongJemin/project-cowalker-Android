package com.jemcom.cowalker.Hyunmin.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.jemcom.cowalker.Hyunmin.Adapter.ImageAdapter
import com.jemcom.cowalker.Jemin.Activity.ApplyActivity
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_project_intro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectIntroActivity : AppCompatActivity(),View.OnClickListener {

    internal lateinit var networkService: NetworkService
    var CHECK_NUM = 0

    override fun onClick(v: View?) {
        when(v)
        {

            btn_join -> {
                val nextIntent = Intent(this, MainActivity::class.java)
                startActivity(nextIntent)
            }

            see_more ->  {
                see_more.visibility = View.GONE
                see_close.visibility = View.VISIBLE
                tv_short.maxLines = Integer.MAX_VALUE
            }

            see_close -> {
                see_close.visibility = View.GONE
                see_more.visibility = View.VISIBLE
                tv_short.maxLines = 2
            }

            scrap_btn -> {
                    if(CHECK_NUM == 0) {
                        scrap_btn.isSelected = true
                        CHECK_NUM = 1
                    }
                    else {
                        scrap_btn.isSelected = false
                        CHECK_NUM = 0
                    }
                }

            projectIntro_profile_iv -> {
                get(v!!)
            }

            projectIntro_name_tv -> {
                get(v!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_intro)

        var introTitle : String
        //introTitle = intent.getStringExtra("title")

       // intro_title_tv.setText(title)

       intro_title_tv.text = intent.getStringExtra("title")

        val viewPager = findViewById<ViewPager>(R.id.image_swipe)
        val adapter = ImageAdapter(this)
        viewPager.adapter = adapter
        networkService = ApplicationController.instance.networkSerVice

        btn_join.setOnClickListener(this)
        see_more.setOnClickListener(this)
        see_close.setOnClickListener(this)
        scrap_btn.setOnClickListener(this)
        projectIntro_profile_iv.setOnClickListener(this)

    }

    fun get(v : View)
    {
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val user_idx = "2"
        var getMypageOtherResponse = networkService.getMypageOther(" eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDYsImlhdCI6MTUzMTA3OTA5MywiZXhwIjoxNTMzNjcxMDkzfQ.ZWdFvvvkoW9wnD5wBUT8zvKlpks0krr_Z-DMgfl8qPI",user_idx)
        getMypageOtherResponse.enqueue(object : Callback<GetMypageOtherResponse>{
            override fun onFailure(call: Call<GetMypageOtherResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetMypageOtherResponse>?, response: Response<GetMypageOtherResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body()
                    if(data.user_status.equals("타인의 페이지"))
                    {
                        var intent = Intent(v.context,MainActivity::class.java)
                        intent.putExtra("status","otherpage")
                        startActivity(intent)
                    }
                    else
                    {
                        var intent = Intent(v.context,MainActivity::class.java)
                        intent.putExtra("status","mypage")
                        startActivity(intent)
                    }
                }
                else Toast.makeText(v.context,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}