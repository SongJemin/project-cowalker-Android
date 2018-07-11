package com.jemcom.cowalker.Hyunmin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Hyunmin.Adapter.ImageAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetIntroOtherResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_profile_more.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileMoreActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService // 필수로 적어야 함.
    lateinit var requestManager : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_more)
        networkService = ApplicationController.instance.networkSerVice // networkService에서 AC를 통해 직접 접근하는 것.
        requestManager = Glide.with(this)

        get() // get 함수 쓴다. onCreate가 가장 먼저 보이는 것이기에! 아래에서 함수 선언~
    }

    fun get()
    {
        var user_idx = "2" // 현재는 임시값을 넣어준 것
        var getProfieOtherResponse = networkService.getIntroOther(user_idx)

        getProfieOtherResponse.enqueue(object : Callback<GetIntroOtherResponse>{ // object 객체 무조건 밑에 override해주기, 내용 없어도 해주기.
            override fun onFailure(call: Call<GetIntroOtherResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext, "서버 연결 실패", Toast.LENGTH_SHORT).show() // 실패시 토스트 메시지 띄워주자.
            }
            override fun onResponse(call: Call<GetIntroOtherResponse>?, response: Response<GetIntroOtherResponse>?) {
                if(response!!.isSuccessful) // 성공 했을 때
                {
                    var data = response.body().result // data 변수를 선언하고, response, body에서 result를 읽어온다.

                    profile_more_contents_tv.setText(data.intro_contents) // 선언된 TextView에서 setText하는 거! -> data의 배열 처음 값 0을 가져온다.

                    var viewPager = findViewById<ViewPager>(R.id.image_swipe)
                    var adapter = ImageAdapter(applicationContext, requestManager, data.intro_img_url)

                    viewPager.adapter = adapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show() // 실패시에 또 토스트 메시지 띄워주자.
            }
        })
    }
}