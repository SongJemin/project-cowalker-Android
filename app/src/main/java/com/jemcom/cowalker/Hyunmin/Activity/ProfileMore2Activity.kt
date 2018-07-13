package com.jemcom.cowalker.Hyunmin.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Hyunmin.Adapter.ImageAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetIntroOtherResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.MyInfoEditActivity
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_profile_more2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileMore2Activity : AppCompatActivity(),View.OnClickListener {

    lateinit var networkService: NetworkService // 필수로 적어야 함.
    lateinit var requestManager : RequestManager

    override fun onClick(v: View?) {
        when(v)
        {
            profile_edit_btn -> {
                var intent = Intent(applicationContext,MyInfoEditActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_more2)
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
        networkService = ApplicationController.instance.networkSerVice // networkService에서 AC를 통해 직접 접근하는 것.
        requestManager = Glide.with(this)
        profile_edit_btn.setOnClickListener(this)

        get()
    }
    fun get()
    {
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getProfieMineResponse = networkService.getIntroMine(token)

        getProfieMineResponse.enqueue(object : Callback<GetIntroOtherResponse>{ // object 객체 무조건 밑에 override해주기, 내용 없어도 해주기.
            override fun onFailure(call: Call<GetIntroOtherResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext, "서버 연결 실패", Toast.LENGTH_SHORT).show() // 실패시 토스트 메시지 띄워주자.
                System.out.println("이유가 " + t.toString())
            }
            override fun onResponse(call: Call<GetIntroOtherResponse>?, response: Response<GetIntroOtherResponse>?) {
                if(response!!.isSuccessful) // 성공 했을 때
                {
                    var data = response.body().result // data 변수를 선언하고, response, body에서 result를 읽어온다.
                    profile_more_contents2_tv.setText(data.intro_contents) // 선언된 TextView에서 setText하는 거! -> data의 배열 처음 값 0을 가져온다.

                    var viewPager = findViewById<ViewPager>(R.id.image_swipe)
                    var adapter = ImageAdapter(applicationContext, requestManager, data.intro_img_url)

                    viewPager.setAdapter(adapter)
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show() // 실패시에 또 토스트 메시지 띄워주자.
            }
        })
    }
}

