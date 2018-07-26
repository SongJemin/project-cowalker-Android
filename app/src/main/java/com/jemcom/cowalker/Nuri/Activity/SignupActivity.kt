package com.jemcom.cowalker.Nuri.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetSignupResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostSignup
import com.jemcom.cowalker.Network.Post.Response.PostSignupResponse
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var networkService: NetworkService
    lateinit var email : String
    lateinit var password : String
    lateinit var passwordck : String
    lateinit var name : String
    val Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{7,16}$"

    override fun onClick(v: View?) {
        var check = 0
        when(v)
        {
            signup_ok_btn -> {
                signup_ok_btn.isSelected = true
                password = signup_pw_ed.text.toString()
                passwordck = signup_pwcheck_ed.text.toString()
                name = signup_name_ed.text.toString()
                if(email.length > 0 && password.length > 0 && passwordck.length > 0 && name.length > 0) {
                    if (!Passwordvalidate(password)) {
                        check = 1
                        signup_pw_tv.setText("영문자,숫자 조합 7글자 이상 입력해주세요.")
                        signup_pw_tv.setVisibility(View.VISIBLE)
                        signup_pw_tv.setTextColor(Color.parseColor("#ff6464"))
                        signup_pw_ed.background.setColorFilter(Color.parseColor("#ff6464"), PorterDuff.Mode.SRC_IN)
                    }
                    if (!password.equals(passwordck)) {
                        check = 1
                        signup_pwcheck_tv.setText("일치하지 않습니다.")
                        signup_pwcheck_tv.setVisibility(View.VISIBLE)
                        signup_pwcheck_tv.setTextColor(Color.parseColor("#ff6464"))
                        signup_pwcheck_ed.background.setColorFilter(Color.parseColor("#ff6464"), PorterDuff.Mode.SRC_IN)
                    }
                    if (check != 1) post()
                }
                else
                {
                    Toast.makeText(applicationContext,"정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show()
                    signup_ok_btn.isSelected = false
                }
            }

            signup_emailcheck_tv -> {
                email = signup_email_ed.text.toString()
                getCheck()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_email_tv.setVisibility(View.INVISIBLE)
        signup_pwcheck_tv.setVisibility(View.INVISIBLE)
        signup_name_tv.setVisibility(View.INVISIBLE)

        signup_emailcheck_tv.setOnClickListener(this)
        signup_ok_btn.setOnClickListener(this)

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
        Log.v("TAG","네트워크서비스 instance값 확인" + ApplicationController.instance)
        Log.v("TAG","네트워크서비스 networkService값 확인" + ApplicationController.instance.networkSerVice)
    }

    fun post()
    {
        System.out.println(email + "///" + password + "///" + name)
        var data = PostSignup(email, password, name)
        var postSignupResponse = networkService.postSignup(data)

        postSignupResponse.enqueue(object : retrofit2.Callback<PostSignupResponse>{

            override fun onResponse(call: Call<PostSignupResponse>, response: Response<PostSignupResponse>) {
                if(response.isSuccessful){
                    var intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<PostSignupResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getCheck()
    {
        var getSignupckResponse = networkService.getSignupCheck(email)

        getSignupckResponse.enqueue(object : retrofit2.Callback<GetSignupResponse>{
            override fun onFailure(call: Call<GetSignupResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(call: Call<GetSignupResponse>?, response: Response<GetSignupResponse>?) {
                if(response!!.isSuccessful)
                {
                    var message = response.body()
                    signup_email_tv.setText("사용 가능한 이메일 입니다.")
                    signup_email_tv.setVisibility(View.VISIBLE)
                    signup_email_tv.setTextColor(Color.parseColor("#64dfff"))
                    signup_email_ed.background.setColorFilter(Color.parseColor("#64dfff"),PorterDuff.Mode.SRC_IN)
                }
                else{
                    signup_email_tv.setTextColor(Color.parseColor("#ff6464"))
                    signup_email_tv.setText("이미 등록된 이메일 입니다.")
                    signup_email_tv.setVisibility(View.VISIBLE)
                    signup_email_ed.background.setColorFilter(Color.parseColor("#ff6464"),PorterDuff.Mode.SRC_IN)
                }
            }
        })
    }

    fun Passwordvalidate(pw : String) : Boolean
    {
        var pattern = Pattern.compile(Passwrod_PATTERN)
        var matcher = pattern.matcher(pw)
        return matcher.matches()
    }
}
