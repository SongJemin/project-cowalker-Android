package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostJoin
import com.jemcom.cowalker.Network.Post.Response.PostJoinResponse
import com.jemcom.cowalker.R
import com.jemcom.cowalker.R.id.apply_apply_btn
import kotlinx.android.synthetic.main.activity_apply.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 지원서 액티비티
class ApplyActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v)
        {
            apply_apply_btn ->{
                post()
            }
        }
    }

    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        networkService = ApplicationController.instance.networkSerVice

        apply_apply_btn.setOnClickListener(this)
    }

    fun post()
    {
        var introduce = apply_introduce_edit.text.toString()
        var portfolio = apply_portfolio_url_edit.text.toString()
        var recruit_idx = "5b3e2ee66e351c1db0b33450"
        var answers : ArrayList<String> = ArrayList()
        var phone = apply_phone_edit.text.toString()
        var project_idx = "5b3f3f28a989031a3ef84e3c"
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var position = "기획자"

        answers.add(apply_question1_edit.text.toString())
        answers.add(apply_question2_edit.text.toString())

        var data = PostJoin(introduce,portfolio, recruit_idx,project_idx,position, answers, phone)
        var postJoinResponse = networkService.postJoin(token,data)

        postJoinResponse.enqueue(object : Callback<PostJoinResponse>{
            override fun onFailure(call: Call<PostJoinResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostJoinResponse>?, response: Response<PostJoinResponse>?) {
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