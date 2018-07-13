package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
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
    var email : String = ""
    var password : String = ""
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
                if(email.length > 0 && password.length > 0) {
                    post()
                }
                else
                {
                    Toast.makeText(applicationContext,"정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show()
                }
            }

            login_title_tv -> {
                val intent = Intent(applicationContext,RecommendActivity::class.java)
                startActivity(intent)
            }

            login_auto_txt -> {
                if(auto == false) {
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
        login_check_btn.setVisibility(View.INVISIBLE)
        login_auto_txt.setOnClickListener(this)
        login_ok_btn.setOnClickListener(this)
        login_signup_btn.setOnClickListener(this)
        login_nonmem_tv.setOnClickListener(this)
        login_title_tv.setOnClickListener(this)

        networkService = ApplicationController.instance.networkSerVice
        val pref = applicationContext.getSharedPreferences("auto",Activity.MODE_PRIVATE)
        val autoLogin = pref.getString("auto","")

        if(autoLogin.length > 0)
        {
            var intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_ok_btn.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
                var action = event!!.action

                if(action == MotionEvent.ACTION_DOWN)
                {
                    login_ok_btn.setImageResource(R.drawable.login_press)
                }
                else if(action == MotionEvent.ACTION_UP)
                {
                    login_ok_btn.setImageResource(R.drawable.login_btn)
                }
                return false
            }
        })

        login_pw_ed.addTextChangedListener(object : TextWatcher{

            override fun afterTextChanged(p0: Editable?) {
                email = login_email_ed.text.toString()
                password = login_pw_ed.text.toString()
                if(email.length > 0 && password.length > 0)
                {
                    login_ok_btn.isSelected =true
                }
                else login_ok_btn.isSelected = false
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun post()
    {
        var fcm_token = FirebaseInstanceId.getInstance().getToken();
        var data = PostLogin(email,password,fcm_token)
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
                        val pref = applicationContext.getSharedPreferences("auto",Activity.MODE_PRIVATE)
                        var autoLogin : SharedPreferences.Editor = pref.edit()
                        if(auto == true)
                        {
                            autoLogin.putString("auto",message.token)
                            autoLogin.commit()
                            login_ok_btn.isSelected = true
                        }
                        autoLogin.putString("token",message.token)
                        autoLogin.commit()

                        var intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else
                {
                    Toast.makeText(applicationContext,"정보가 일치하지 않습니다",Toast.LENGTH_SHORT).show()
                    login_ok_btn.isSelected = false
                }
            }
        })
    }
}