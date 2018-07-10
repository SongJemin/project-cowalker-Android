package com.jemcom.cowalker.Hyunmin.Activity

import android.app.Activity
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.activity_mypage_projectlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageProjectlistActivity : AppCompatActivity() {

    var projectMineItems : ArrayList<ProjectItem> = ArrayList()
    var projectParticipateItems : ArrayList<ProjectItem> = ArrayList()
    var projectApplyItems : ArrayList<ProjectItem> = ArrayList()
    lateinit var projectMineAdapter : ProjectAdapter
    lateinit var networkService: NetworkService
    lateinit var requestManager : RequestManager
    lateinit var pref : SharedPreferences
    lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_projectlist)

        networkService = ApplicationController.instance.networkSerVice
        requestManager = Glide.with(this)
        pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        token  = pref.getString("token","")

        getCreate()
        getParticipate()
        getApply()

    }

    fun getCreate()
    {
        var getProjectMine = networkService.getProjectMine("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA2NzAxNTMsImV4cCI6MTUzMzI2MjE1M30.BdRb0yary7AY8_yi8MDRDXuXrW19QSqRJI-9Xin3SXs")

        getProjectMine.enqueue(object : Callback<GetProjectMineResponse>{
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
                        projectMineItems.add(ProjectItem("https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531246857588.jpg",data[i].title,data[i].aim + " / " + data[i].department + " / " + data[i].area))
                    }
                    System.out.println("사이즈222 : " + projectMineItems.size)
                    projectMineAdapter = ProjectAdapter(projectMineItems,requestManager)
                    projectlist_create_rv.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, false)
                    projectlist_create_rv.adapter = projectMineAdapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getParticipate()
    {
        var getParticipateResponse = networkService.getProjectMineParticipate("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA2NzAxNTMsImV4cCI6MTUzMzI2MjE1M30.BdRb0yary7AY8_yi8MDRDXuXrW19QSqRJI-9Xin3SXs")

        getParticipateResponse.enqueue(object : Callback<GetProjectMineParticipateResponse>{
            override fun onFailure(call: Call<GetProjectMineParticipateResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetProjectMineParticipateResponse>?, response: Response<GetProjectMineParticipateResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result

                    if(data.size > 0) participate_tv.setVisibility(View.GONE)
                    else projectlist_participate_rv.setVisibility(View.GONE)
                    for(i in 0..data.size-1)
                    {
                        projectParticipateItems.add(ProjectItem(data[i].img_url[0],data[i].title,data[i].aim + " / " + data[i].department + " / " + data[i].area))
                    }

                    projectMineAdapter = ProjectAdapter(projectParticipateItems,requestManager)
                    projectlist_participate_rv.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, false)
                    projectlist_participate_rv.adapter = projectMineAdapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getApply()
    {
        var getApplyResponse = networkService.getProjectMineApply("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA2NzAxNTMsImV4cCI6MTUzMzI2MjE1M30.BdRb0yary7AY8_yi8MDRDXuXrW19QSqRJI-9Xin3SXs")

        getApplyResponse.enqueue(object : Callback<GetProjectMineApplyResponse>{
            override fun onFailure(call: Call<GetProjectMineApplyResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetProjectMineApplyResponse>?, response: Response<GetProjectMineApplyResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result

                    if(data.size > 0) apply_null_tv.setVisibility(View.GONE)
                    else projectlist_apply_rv.setVisibility(View.GONE)
                    for(i in 0..data.size-1)
                    {
                        projectApplyItems.add(ProjectItem(data[i].img_url[0],data[i].title,data[i].aim + " / " + data[i].department + " / " + data[i].area))
                    }

                    projectMineAdapter = ProjectAdapter(projectApplyItems,requestManager)
                    projectlist_apply_rv.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, false)
                    projectlist_apply_rv.adapter = projectMineAdapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
