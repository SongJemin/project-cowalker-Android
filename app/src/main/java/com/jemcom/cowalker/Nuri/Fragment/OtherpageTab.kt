package com.jemcom.cowalker.Nuri.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Hyunmin.Activity.MypageProjectlistActivity
import com.jemcom.cowalker.Hyunmin.Activity.ProfileEditActivity
import com.jemcom.cowalker.Hyunmin.Activity.ProfileMoreActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetMypage
import com.jemcom.cowalker.Network.Get.GetOtherpage
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.MessageActivity
import com.jemcom.cowalker.Nuri.Activity.Notice_messageActivity
import com.jemcom.cowalker.Nuri.Activity.OtherpageProjectlistActivity
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_otherpage.*
import kotlinx.android.synthetic.main.fragment_otherpage.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherpageTab : Fragment(), View.OnClickListener {

    lateinit var data : ArrayList<GetOtherpage>
    override fun onClick(v: View?) {
        when(v)
        {
            otherpage_message_btn->{
                val intent = Intent(activity, Notice_messageActivity::class.java)
                intent.putExtra("partner_id",data[0].user_idx)
                startActivity(intent)
            }
            otherpage_project_btn ->{
                val intent = Intent(activity, OtherpageProjectlistActivity::class.java)
                intent.putExtra("user_idx",user_idx)
                startActivity(intent)
            }
            otherpage_intro_btn -> {
                val intent = Intent(activity, ProfileMoreActivity::class.java)
                Log.v("TAG", "아더페이지 유저명 = " + user_idx)
                intent.putExtra("user_idx",user_idx)
                startActivity(intent)
            }
        }
    }

    var user_idx : String = ""
    lateinit var networkService : NetworkService
    lateinit var requestManager : RequestManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otherpage, container, false)

        networkService = ApplicationController.instance.networkSerVice
        requestManager = Glide.with(this)
        view.otherpage_project_btn.setOnClickListener(this)
        view.otherpage_intro_btn.setOnClickListener(this)
        view.otherpage_message_btn.setOnClickListener(this)
        get(view)

        return view
    }

    fun get(v : View)
    {
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val extra = arguments
        user_idx = extra!!.getString("user_idx")
        var getMypageResponse = networkService.getMypageOther(token,user_idx)
        Log.v("TAG","타인 페이지 토큰 = " + token)
        Log.v("TAG","타인 페이지 유저번호 = " + user_idx)
        getMypageResponse.enqueue(object : Callback<GetMypageOtherResponse> {
            override fun onFailure(call: Call<GetMypageOtherResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetMypageOtherResponse>?, response: Response<GetMypageOtherResponse>?) {
                Log.v("TAG", "통신은 성공")
                if(response!!.isSuccessful)
                {

                    data = response.body().data
                    Log.v("TAG", "타인 페이지 데이터 = "+data.toString())
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