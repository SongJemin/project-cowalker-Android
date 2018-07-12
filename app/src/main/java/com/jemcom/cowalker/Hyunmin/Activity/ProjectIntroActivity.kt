package com.jemcom.cowalker.Hyunmin.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.jemcom.cowalker.Hyunmin.Adapter.ImageAdapter
import com.jemcom.cowalker.Jemin.Activity.ApplyActivity
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.Jemin.Activity.ProjectIntroCreaterActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetProjectMessage
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.RecruitDetailActivity
import com.jemcom.cowalker.Nuri.Adapter.RecruitListGetAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_project_intro.*
import kotlinx.android.synthetic.main.activity_project_intro_creater.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectIntroActivity : AppCompatActivity(),View.OnClickListener {
    var recruitData : java.util.ArrayList<GetRecruitList> = java.util.ArrayList<GetRecruitList>()
    internal lateinit var networkService: NetworkService
    var CHECK_NUM = 0
    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListGetAdapter : RecruitListGetAdapter
    var title: String = ""
    var summary: String = ""
    var aim: String = ""
    var department: String = ""
    var area: String = ""
    var explain: String = ""
    var name: String = ""
    var img_url: String = ""
    var project_idx: String = ""
    var recruit_idx: String = ""

    override fun onClick(v: View) {
        var idx = recruit_list_recyclerview2!!.getChildAdapterPosition(v)
        recruit_idx = recruitData!![idx].recruit_idx!!
        Log.v("TAG", "선택한 모집번호 = "+ recruit_idx)

        val intent = Intent(v.context, RecruitDetailActivity::class.java)
        intent.putExtra("project_idx", project_idx)
        intent.putExtra("recruit_idx", recruit_idx)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_intro)

        var introTitle : String
        //introTitle = intent.getStringExtra("title")

       // intro_title_tv.setText(title)

       intro_title_tv.text = intent.getStringExtra("title")

        val viewPager = findViewById<ViewPager>(R.id.image_swipe)
        val adapter = ImageAdapter(this)
        viewPager.adapter = adapter
        networkService = ApplicationController.instance.networkSerVice
        recruitListItems = ArrayList()

        val intent = intent
        if(intent != null) {
            if(intent.getStringExtra("title") != null) title = intent.getStringExtra("title")
            if(intent.getStringExtra("summary") != null) summary = intent.getStringExtra("summary")
            if(intent.getStringExtra("aim") != null) aim = intent.getStringExtra("aim")
            if(intent.getStringExtra("department") != null) department = intent.getStringExtra("department")
            if(intent.getStringExtra("area") != null) area = intent.getStringExtra("area")
            if(intent.getStringExtra("explain") != null) explain = intent.getStringExtra("explain")
            if(intent.getStringExtra("name") != null) name = intent.getStringExtra("name")
            if(intent.getStringExtra("img_url") != null) img_url = intent.getStringExtra("img_url")
            if(intent.getStringExtra("project_idx") != null) project_idx = intent.getStringExtra("project_idx")
        }
        Log.v("TAG","참여자 화면 프로젝트넘버 = "+project_idx)

        getList()
        btn_join.setOnClickListener(this)
        see_more.setOnClickListener(this)
        see_close.setOnClickListener(this)
        projectIntro_profile_iv.setOnClickListener(this)

        btn_join.setOnClickListener{
            val nextIntent = Intent(this, RecruitDetailActivity::class.java)
            startActivity(nextIntent)
        }
        see_more.setOnClickListener  {
            see_more.visibility = View.GONE
            see_close.visibility = View.VISIBLE
            tv_short.maxLines = Integer.MAX_VALUE
        }

        see_close.setOnClickListener {
            see_close.visibility = View.GONE
            see_more.visibility = View.VISIBLE
            tv_short.maxLines = 2
        }

        projectIntro_profile_iv.setOnClickListener {
            get()
        }

        projectIntro_name_tv.setOnClickListener {
            get()
        }

    }

    fun get()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val user_idx = "2"
        var getMypageOtherResponse = networkService.getMypageOther(" eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDYsImlhdCI6MTUzMTA3OTA5MywiZXhwIjoxNTMzNjcxMDkzfQ.ZWdFvvvkoW9wnD5wBUT8zvKlpks0krr_Z-DMgfl8qPI",user_idx)
        getMypageOtherResponse.enqueue(object : Callback<GetMypageOtherResponse>{
            override fun onFailure(call: Call<GetMypageOtherResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetMypageOtherResponse>?, response: Response<GetMypageOtherResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body()
                    if(data.user_status.equals("타인의 페이지"))
                    {
                        var intent = Intent(this@ProjectIntroActivity,MainActivity::class.java)
                        intent.putExtra("status","otherpage")
                        startActivity(intent)
                    }
                    else
                    {
                        var intent = Intent(this@ProjectIntroActivity,MainActivity::class.java)
                        intent.putExtra("status","mypage")
                        startActivity(intent)
                    }
                }
                else Toast.makeText(this@ProjectIntroActivity
                        ,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getList()
    {

        var getRecruitListResponse = networkService.getRecruitList(project_idx)
        //var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse>{
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                Log.v("TAG","모집 리스트 통신")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","모집 리스트 받아오기")
                    recruitData = response.body().result
                    Log.v("TAG","모집 리스트 값 = "+recruitData.toString())

                    for(i in 0..recruitData.size-1)
                    {

                        recruitListItems.add(RecruitListItem(recruitData[i].position,recruitData[i].number,recruitData[i].task,recruitData[i].dday))
                    }
                    recruitListGetAdapter = RecruitListGetAdapter(recruitListItems)
                    recruitListGetAdapter.setOnItemClickListener(this@ProjectIntroActivity)
                    recruit_list_recyclerview2.layoutManager = LinearLayoutManager(applicationContext)
                    recruit_list_recyclerview2.adapter = recruitListGetAdapter
                }
                //else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@ProjectIntroActivity, MainActivity::class.java)
        startActivity(intent)
    }


}