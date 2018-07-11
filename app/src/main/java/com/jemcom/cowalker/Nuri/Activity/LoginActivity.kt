package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostLogin
import com.jemcom.cowalker.Network.Post.Response.PostLoginResponse
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var networkService: NetworkService
    lateinit var email : String
    lateinit var password : String
    var auto : Boolean = false

    override fun onClick(v: View?) {
        when(v)
        {
            login_signup_btn -> {
                val intent= Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
            }

            login_nonmem_tv -> {
                val intent= Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

            login_ok_btn -> {
                email = login_email_ed.text.toString()
                password = login_pw_ed.text.toString()
                login_ok_btn.isSelected = true
                if(email.length > 0 && password.length > 0) {
                    post()
                }
                else
                {
                    Toast.makeText(applicationContext,"정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show()
                    login_ok_btn.isSelected = false
                }
            }

            login_auto_txt -> {
                if(login_auto_txt.textColors.defaultColor != Color.parseColor("#444444")) {
                    login_check_btn.setVisibility(View.VISIBLE)
                    login_auto_txt.setTextColor(Color.parseColor("#444444"))
                    auto = true
                }
                else
                {
                    login_check_btn.setVisibility(View.INVISIBLE)
                    login_auto_txt.setTextColor(Color.parseColor("#c5c5c5"))
                    auto=false
                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_check_btn.setVisibility(View.INVISIBLE)
        login_auto_txt.setOnClickListener(this)
        login_ok_btn.setOnClickListener(this)
        login_signup_btn.setOnClickListener(this)
        login_nonmem_tv.setOnClickListener(this)

        networkService = ApplicationController.instance.networkSerVice
        val pref = applicationContext.getSharedPreferences("auto",Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        Log.v("TAG","토큰 값 = " + token);

        if(token.length > 0)
        {
            var intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("token",token)
            startActivity(intent)
            finish()
        }
    }

    fun post()
    {
        var data = PostLogin(email,password)
        var postLoginResponse = networkService.postLogin(data)

        postLoginResponse.enqueue(object : Callback<PostLoginResponse>{
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                var message = response!!.body()
                if(response!!.isSuccessful)
                {
                    if(message.message.equals("login success"))
                    {
                        if(auto == true)
                        {
                            val pref = applicationContext.getSharedPreferences("auto",Activity.MODE_PRIVATE)
                            var autoLogin : SharedPreferences.Editor = pref.edit()
                            autoLogin.putString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMTcsImlhdCI6MTUzMTE3MzIzOSwiZXhwIjoxNTMzNzY1MjM5fQ.taqF_rP7P2DzGiSTT234wv3dqjjsTBLA0J01K-PDlxk")
                            autoLogin.commit()
                        }

                        var intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(message.message.equals("wrong password"))
                    {
                        Toast.makeText(applicationContext,"비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show()
                        login_ok_btn.isSelected = false
                    }
                    else if(message.message.equals("wrong email"))
                    {
                        Toast.makeText(applicationContext,"이메일이 틀렸습니다",Toast.LENGTH_SHORT).show()
                        login_ok_btn.isSelected = false
                    }
                }
            }
        })
    }
}
