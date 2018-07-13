package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostMessageSend
import com.jemcom.cowalker.Network.Post.Response.PostJoinResponse
import com.jemcom.cowalker.Network.Post.Response.PostMessageResponse
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_notice_message.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notice_messageActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            notice_message_iv -> {
                post()
//                var intent = Intent(applicationContext,)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_message)
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

        notice_message_iv.setOnClickListener(this)
    }

    fun post() {
        var content = PostMessageSend(notice_message_ed.text.toString())
        var user_id = intent.getStringExtra("partner_id")
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var postMessageSend = networkService.postMessage(token,content,user_id)

        postMessageSend.enqueue(object : Callback<PostMessageResponse>{
            override fun onFailure(call: Call<PostMessageResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<PostMessageResponse>?, response: Response<PostMessageResponse>?) {
                if(response!!.isSuccessful)
                {
                    var message = response.body()
                    Toast.makeText(applicationContext,"성공",Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
