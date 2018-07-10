package com.jemcom.cowalker.Nuri.Fragment

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
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_otherpage.*
import kotlinx.android.synthetic.main.fragment_otherpage.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherpageTab : Fragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v)
        {
            otherpage_project_btn ->{
                val intent = Intent(activity, MypageProjectlistActivity::class.java)
                startActivity(intent)
            }
        }
    }

    lateinit var networkService : NetworkService
    lateinit var requestManager : RequestManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otherpage, container, false)

        networkService = ApplicationController.instance.networkSerVice
        requestManager = Glide.with(this)

        get(view)

        return view
    }

    fun get(v : View)
    {
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val user_idx = "2"
        var getMypageResponse = networkService.getMypageOther("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA5NTE1ODMsImV4cCI6MTUzMzU0MzU4M30.90d2qcRcikydx8R-lMMyLgcYGcAxY0Poi61a-NGpujY",user_idx)

        getMypageResponse.enqueue(object : Callback<GetMypageOtherResponse> {
            override fun onFailure(call: Call<GetMypageOtherResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetMypageOtherResponse>?, response: Response<GetMypageOtherResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().data
                    v.otherpage_name_tv.setText(data[0].name)
                    v.otherpage_role_tv.setText(data[0].position)
                    v.otherpage_summary_tv.setText(data[0].introduce)
                    v.otherpage_aim_tv.setText(data[0].aim)
                    v.otherpage_department_tv.setText(data[0].department)
                    v.otherpage_area_tv.setText(data[0].area)
                    v.otherpage_link_tv.setText(data[0].portfolio_url)
                    requestManager.load(data[0].profile_url).into(v.otherpage_profile_img)
                    requestManager.load(data[0].background_url).into(v.otherpage_background_img)
                }
                else Toast.makeText(v.context,"실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}