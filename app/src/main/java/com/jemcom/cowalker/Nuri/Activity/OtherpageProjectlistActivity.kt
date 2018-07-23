package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetProjectMineApplyResponse
import com.jemcom.cowalker.Network.Get.Response.GetProjectMineParticipateResponse
import com.jemcom.cowalker.Network.Get.Response.GetProjectMineResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Adapter.ProjectAdapter
import com.jemcom.cowalker.Nuri.Item.ProjectItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_otherpage_projectlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherpageProjectlistActivity : AppCompatActivity() {

    var projectMineItems : ArrayList<ProjectItem> = ArrayList()
    var projectParticipateItems : ArrayList<ProjectItem> = ArrayList()
    lateinit var projectMineAdapter : ProjectAdapter
    lateinit var networkService: NetworkService
    lateinit var requestManager : RequestManager
    lateinit var pref : SharedPreferences
    lateinit var token : String
    lateinit var user_idx : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otherpage_projectlist)
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
        requestManager = Glide.with(this)
        pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        token  = pref.getString("token","")
        user_idx = intent.getStringExtra("user_idx")

        getCreate()
        getParticipate()

    }

    fun getCreate()
    {
        var getProjectMine = networkService.getProjectOther(token,user_idx)

        getProjectMine.enqueue(object : Callback<GetProjectMineResponse> {
            override fun onFailure(call: Call<GetProjectMineResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetProjectMineResponse>?, response: Response<GetProjectMineResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result

                    if(data.size > 0) create_tv.setVisibility(View.GONE)
                    else projectlist_create_rv.setVisibility(View.GONE)
                    for(i in 0..data.size-1)
                    {
                        projectMineItems.add(ProjectItem("https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531246857588.jpg",data[i].title,data[i].aim + " / " + data[i].department + " / " + data[i].area,data[i].project_idx))
                    }
                    projectMineAdapter = ProjectAdapter(projectMineItems,requestManager,applicationContext)
                    projectlist_create_rv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    projectlist_create_rv.adapter = projectMineAdapter
                }
                else Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getParticipate()
    {
        var getParticipateResponse = networkService.getProjectOtherParticipate("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA2NzAxNTMsImV4cCI6MTUzMzI2MjE1M30.BdRb0yary7AY8_yi8MDRDXuXrW19QSqRJI-9Xin3SXs",user_idx)

        getParticipateResponse.enqueue(object : Callback<GetProjectMineParticipateResponse> {
            override fun onFailure(call: Call<GetProjectMineParticipateResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetProjectMineParticipateResponse>?, response: Response<GetProjectMineParticipateResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result

                    if(data.size > 0) participate_tv.setVisibility(View.GONE)
                    else projectlist_participate_rv.setVisibility(View.GONE)
                    for(i in 0..data.size-1)
                    {
                        projectParticipateItems.add(ProjectItem(data[i].img_url[0],data[i].title,data[i].aim + " / " + data[i].department + " / " + data[i].area,data[i]._id))
                    }

                    projectMineAdapter = ProjectAdapter(projectParticipateItems,requestManager,applicationContext)
                    projectlist_participate_rv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    projectlist_participate_rv.adapter = projectMineAdapter
                }
                else Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

}