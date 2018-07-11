package com.jemcom.cowalker.Hyunmin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Hyunmin.Activity.MypageProjectlistActivity
import com.jemcom.cowalker.Hyunmin.Activity.ProfileEditActivity
import com.jemcom.cowalker.Hyunmin.Activity.ProfileMore2Activity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMypageResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.Nuri.Activity.OtherpageProjectlistActivity
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageTab : Fragment(),View.OnClickListener {

    override fun onClick(v: View?) {
        when(v)
        {
            logout_btn -> {
                val pref = v!!.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                val editor = pref.edit()
                editor.clear()
                editor.commit()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            mypage_project_btn ->{
                val intent = Intent(activity, MypageProjectlistActivity::class.java)
                startActivity(intent)
            }

            mypage_edit_btn -> {
                val intent = Intent(activity, ProfileEditActivity::class.java)
                startActivity(intent)
            }

            mypage_intro_btn -> {
                val intent = Intent(activity, ProfileMore2Activity::class.java)
                startActivity(intent)
            }
        }
    }

    lateinit var networkService : NetworkService
    lateinit var requestManager : RequestManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        networkService = ApplicationController.instance.networkSerVice
        requestManager = Glide.with(this)
        view.logout_btn.setOnClickListener(this)
        view.mypage_project_btn.setOnClickListener(this)
        view.mypage_edit_btn.setOnClickListener(this)
        view.mypage_intro_btn.setOnClickListener(this)
        get(view)

        return view
    }

    fun get(v : View)
    {
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getMypageResponse = networkService.getMypage("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA5NTE1ODMsImV4cCI6MTUzMzU0MzU4M30.90d2qcRcikydx8R-lMMyLgcYGcAxY0Poi61a-NGpujY")

        getMypageResponse.enqueue(object : Callback<GetMypageResponse>{
            override fun onFailure(call: Call<GetMypageResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패",Toast.LENGTH_SHORT).show()
                System.out.println("이유가 뭘까?" + t.toString())
            }

            override fun onResponse(call: Call<GetMypageResponse>?, response: Response<GetMypageResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().data
                    v.mypage_name_tv.setText(data[0].name)
                    v.mypage_role_tv.setText(data[0].position)
                    v.mypage_summary_tv.setText(data[0].introduce)
                    v.mypage_aim_tv.setText(data[0].aim)
                    v.mypage_department_tv.setText(data[0].department)
                    v.mypage_area_tv.setText(data[0].area)
                    v.mypage_link_tv.setText(data[0].portfolio_url)
                    requestManager.load(data[0].profile_url).into(v.mypage_profile_img)
                    requestManager.load(data[0].background_url).into(v.mypage_background_img)
                }
                else Toast.makeText(v.context,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}