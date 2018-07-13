package com.jemcom.cowalker.Hyunmin.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Put.Response.PutMyPageResponse
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_mypage_profile_edit.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart

class MypageProfileEditActivity : AppCompatActivity(), View.OnClickListener {
    var projectAim : String? = null
    var projectDepartment : String? = null
    var projectArea : String? = null
    var projectPosition : String? = null
    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            profile_edit_ok_btn -> {
                putEdit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_profile_edit)

        networkService = ApplicationController.instance.networkSerVice
        profile_edit_ok_btn.setOnClickListener(this)

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

        val spinner = findViewById<View>(R.id.role_spin) as Spinner
        val spinner2 = findViewById<View>(R.id.object_spin) as Spinner
        val spinner3 = findViewById<View>(R.id.field_spin) as Spinner
        val spinner4 = findViewById<View>(R.id.location_spin) as Spinner

        val role_list = arrayOf("PM","기획자","디자이너","개발자")
        val purpose_list = arrayOf("창업", "공모전 참여", "스터디", "사이드 프로젝트", "창업", "기타")
        val field_list = arrayOf("블록체인", "IoT", "인공지능", "디자인", "콘텐츠", "기타")
        val location_list = arrayOf("서울", "경기도", "인천", "강원도", "충청도", "전라도", "경상도", "제주도")

        // 역할 에 대한 Spinner
        val adapter1 = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                role_list)
        adapter1.setDropDownViewResource(
                R.layout.spin_dropdown)
        spinner.setAdapter(adapter1)

        // 목적 에 대한 Spinner
        val adapter = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                purpose_list)
        adapter.setDropDownViewResource(
                R.layout.spin_dropdown)
        spinner2.setAdapter(adapter)

        // 분야 에 대한 Spinner
        val adapter2 = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                field_list)
        adapter2.setDropDownViewResource(
                R.layout.spin_dropdown)
        spinner3.setAdapter(adapter2)

        // 지역 에 대한 Spinner
        val adapter3 = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                location_list)
        adapter3.setDropDownViewResource(
                R.layout.spin_dropdown)
        spinner4.setAdapter(adapter3)

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectPosition = "PM"
                } else if (position == 1) {
                    projectPosition = "기획자"
                } else if (position == 2) {
                    projectPosition = "디자이너"
                } else if (position == 3) {
                    projectPosition = "개발자"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })

        spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectAim = "창업"
                } else if (position == 1) {
                    projectAim = "공모전 참여"
                } else if (position == 2) {
                    projectAim = "스터디"
                } else if (position == 3) {
                    projectAim = "사이드 프로젝트"
                } else if (position == 4) {
                    projectAim = "창업"
                } else if (position == 5) {
                    projectAim = "기타"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })
        spinner3.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectDepartment = "블록체인"
                } else if (position == 1) {
                    projectDepartment = "IoT"
                } else if (position == 2) {
                    projectDepartment = "인공지능"
                } else if (position == 3) {
                    projectDepartment = "디자인"
                } else if (position == 4) {
                    projectDepartment = "콘텐츠"
                } else if (position == 5) {
                    projectDepartment = "기타"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })
        spinner4.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectArea = "서울"
                    Log.v("TAG", "장소 = " + projectArea)
                } else if (position == 1) {
                    projectArea = "경기도"
                } else if (position == 2) {
                    projectArea = "인천"
                } else if (position == 3) {
                    projectArea = "강원도"
                } else if (position == 4) {
                    projectArea = "충청도"
                } else if (position == 5) {
                    projectArea = "전라도"
                } else if (position == 6) {
                    projectArea = "경상도"
                } else if (position == 7) {
                    projectArea = "제주도"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })
    }
    fun putEdit()
    {
        val profile_img : MultipartBody.Part? = null
        val background_img : MultipartBody.Part? =null
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token  = pref.getString("token","")
        var putEditResponse = networkService.putMypage(token,
                profile_img,background_img,projectPosition!!,profile_edit_intro_et.text.toString(),profile_edit_url_et.text.toString(),projectAim!!,projectDepartment!!,projectArea!!);

        Log.v("TAG", "마이페이지 포지션 = "+ projectPosition)
        Log.v("TAG", "마이페이지 목적 = "+ projectAim)
        Log.v("TAG", "마이페이지 분야 = "+ projectDepartment)
        Log.v("TAG", "마이페이지 장소 = "+ projectArea)
        putEditResponse.enqueue(object : Callback<PutMyPageResponse>{
            override fun onFailure(call: Call<PutMyPageResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PutMyPageResponse>?, response: Response<PutMyPageResponse>?) {
                if(response!!.isSuccessful)
                {
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    intent.putExtra("status","mypage")
                    startActivity(intent)
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
